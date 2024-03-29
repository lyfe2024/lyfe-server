package lyfe.lyfeBe.comment.service

import lyfe.lyfeBe.auth.service.SecurityUtils.getLoginUserId
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.comment.CommentCreate
import lyfe.lyfeBe.comment.CommentGetsByBoard
import lyfe.lyfeBe.comment.CommentUpdate
import lyfe.lyfeBe.comment.dto.CommentDto
import lyfe.lyfeBe.comment.dto.CommentListDto
import lyfe.lyfeBe.comment.dto.SaveCommentDto
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.error.ForbiddenException
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CommentService(
    private val commentPort: CommentPort,
    private val userPort: UserPort,
    private val boardPort: BoardPort,
) {

    @Transactional
    fun create(commentCreate: CommentCreate): SaveCommentDto {

        val user = userPort.getById(userId = getLoginUserId(userPort))
        val board = boardPort.getById(id = commentCreate.boardId)

        commentCreate.commentGroupId?.let {
            val groupComment = commentPort.getById(id = it)
            require(groupComment.commentGroupId == null) { "대댓글은 대댓글을 달 수 없습니다." }
        }

        val comment = Comment.from(commentCreate, user, board)

        return SaveCommentDto(commentPort.create(comment).id)
    }

    fun getById(commentId: Long): CommentDto {
        return commentPort.getById(id = commentId)
            .run { CommentDto.from(this) }
    }

    /**
     * 해당 게시글의 댓글 전체 조회
     */
    fun getCommentsWithCursorAndBoard(command: CommentGetsByBoard): CommentListDto {
        commentPort.getCommentsWithCursorAndBoard(command.cursorId, command.boardId, command.pageable)
            .let {
                return CommentListDto.toListDto(
                    it.map { CommentDto.from(it) }
                )
            }
    }

    /**
     * 자신의 댓글 전체 조회
     */
    fun getCommentsWithCursorAndUser(cursorId : Long): CommentListDto {
         commentPort.getCommentsWithCursorAndUser(cursorId, getLoginUserId(userPort))
            .let {
                return CommentListDto.toListDto(
                    it.map { CommentDto.from(it) }
                )
            }
    }

    @Transactional
    fun update(commentUpdate: CommentUpdate): SaveCommentDto {
        val comment = commentPort.getById(id = commentUpdate.commentId).update(commentUpdate)

        if (comment.user.id != getLoginUserId(userPort)) {
            throw ForbiddenException("자신의 댓글만 수정할 수 있습니다.")
        }

        return SaveCommentDto(commentPort.update(comment).id)
    }
}
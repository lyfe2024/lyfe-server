package lyfe.lyfeBe.comment.service

import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.comment.*
import lyfe.lyfeBe.comment.dto.CommentDto
import lyfe.lyfeBe.comment.dto.SaveCommentDto
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.error.ForbiddenException
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CommentService(
    private val commentPort: CommentPort,
    private val userPort: UserPort,
    private val boardPort: BoardPort,
) {
    fun create(command: CommentCreate): SaveCommentDto {

        val user = userPort.getById(userId = command.userId)
        val board = boardPort.findById(id = command.boardId)

        command.commentGroupId?.let {
            val groupComment = commentPort.getById(id = it)
            require(groupComment.commentGroupId == null) { "대댓글은 대댓글을 달 수 없습니다." }
        }

        val comment = Comment.from(
            commentCreate = command,
            user = user,
            board = board
        )

        return commentPort.create(comment)
            .let { SaveCommentDto.from(it) }
    }

    fun getById(commentId: Long): CommentDto {
        return commentPort.getById(id = commentId)
            .run { CommentDto.from(this) }
    }

    /**
     * 해당 게시글의 댓글 전체 조회
     */
    fun getCommentsWithCursorAndBoard(command: CommentGetsByBoard): List<Comment> {
        return commentPort.getCommentsWithCursorAndBoard(command.cursorId, command.boardId)
    }

    /**
     * 자신의 댓글 전체 조회
     */
    fun getCommentsWithCursorAndUser(command: CommentGetsByUserId): List<Comment> {
        return commentPort.getCommentsWithCursorAndUser(command.cursorId, command.userId)
    }

    fun update(commentUpdate : CommentUpdate): SaveCommentDto {
        val comment = commentPort.getById(id = commentUpdate.commentId).update(commentUpdate)

        if (comment.user.id != commentUpdate.userId) {
            throw ForbiddenException("자신의 댓글만 수정할 수 있습니다.")
        }

        return commentPort.update(comment)
            .let { SaveCommentDto.from(it) }
    }
}
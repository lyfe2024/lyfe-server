package lyfe.lyfeBe.comment.service

import lyfe.lyfeBe.auth.service.SecurityUtils.getLoginUser
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
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Transactional(readOnly = true)
@Service
class CommentService(
    private val commentPort: CommentPort,
    private val userPort: UserPort,
    private val boardPort: BoardPort,
) {

    @Transactional
    fun create(commentCreate: CommentCreate): SaveCommentDto {
        val user = getLoginUser(userPort)
        checkUserStatus(user)

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
        val comment = commentPort.getCommentsWithBoard(command.boardId)

        val comments = comment.map { comments ->
            val replies = commentPort.getCommentsWithParentCommentIdAndBoard(command.boardId, comments.id)
            CommentDto.from(comments, replies)
        }

        return CommentListDto.toListDto(comments)
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
        val user = getLoginUser(userPort)
        checkUserStatus(user)
        val comment = commentPort.getById(id = commentUpdate.commentId).update(commentUpdate)

        if (comment.user.id != user.id) {
            throw ForbiddenException("자신의 댓글만 수정할 수 있습니다.")
        }

        return SaveCommentDto(commentPort.update(comment).id)
    }

    private fun checkUserStatus(user: User) {
        user.takeIf {
            user.userStatus == UserStatus.WARNING && user.warningAt
                ?.let { Instant.now().isAfter(it) } == true
        }?.apply {
            userPort.update(updateActive())
        }

        val checkUser = getLoginUser(userPort)
        if (checkUser.userStatus != UserStatus.ACTIVE) {
            throw ForbiddenException("댓글을 작성할 수 없습니다.")
        }
    }
}
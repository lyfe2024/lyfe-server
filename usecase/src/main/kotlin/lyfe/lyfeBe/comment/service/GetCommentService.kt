package lyfe.lyfeBe.comment.service

import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.comment.dto.CommentDto
import lyfe.lyfeBe.comment.GetCommentByBoard
import lyfe.lyfeBe.comment.GetCommentByUserId
import lyfe.lyfeBe.comment.port.out.CommentPort
import org.springframework.stereotype.Service

@Service
class GetCommentService(
    private val commentPort: CommentPort
) {
    fun getComment(commentId: Long): CommentDto {
        return commentPort.getById(id = commentId)
            .run { CommentDto.from(this) }
    }

    /**
     * 해당 게시글의 댓글 전체 조회
     */
    fun getCommentsWithCursorAndBoard(command: GetCommentByBoard): List<Comment> {
        return commentPort.getCommentsWithCursorAndBoard(command.cursorId, command.boardId)
    }

    /**
     * 자신의 댓글 전체 조회
     */
    fun getCommentsWithCursorAndUser(command: GetCommentByUserId): List<Comment> {
        return commentPort.getCommentsWithCursorAndUser(command.cursorId, command.userId)
    }
}
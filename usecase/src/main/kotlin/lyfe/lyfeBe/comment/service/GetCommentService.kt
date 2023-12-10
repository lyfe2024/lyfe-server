package lyfe.lyfeBe.comment.service

import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.comment.port.`in`.GetCommentByBoardCommand
import lyfe.lyfeBe.comment.port.`in`.GetCommentByUserIdCommand
import lyfe.lyfeBe.comment.port.out.CommentPort
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class GetCommentService(
    private val commentPort: CommentPort
) {
    fun getComment(commentId: Long): Comment {
        return commentPort.getById(id = commentId)
    }

    /**
     * 해당 게시글의 댓글 전체 조회
     */
    fun findAllByBoard(command: GetCommentByBoardCommand): Page<Comment> {
        var lastCommentId = command.cursorId
        if (command.cursorId == 0L) {
            lastCommentId = commentPort.findLastByBoardId(command.boardId).id
        }
        return commentPort.findAllByBoardId(lastCommentId, command.boardId, command.pageable)
    }

    /**
     * 자신의 댓글 전체 조회
     */
    fun findAllByUserId(command: GetCommentByUserIdCommand): Page<Comment> {
        var lastCommentId = command.cursorId
        if (command.cursorId == 0L) {
            lastCommentId = commentPort.findLastByBoardId(command.boardId).id
        }
        return commentPort.findAllByUserId(lastCommentId, command.userId, command.pageable)
    }
}
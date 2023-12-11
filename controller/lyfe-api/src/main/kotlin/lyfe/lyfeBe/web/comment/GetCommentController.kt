package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.comment.dto.CommentDto
import lyfe.lyfeBe.comment.GetCommentByBoard
import lyfe.lyfeBe.comment.GetCommentByUserId
import lyfe.lyfeBe.comment.service.GetCommentService
import lyfe.lyfeBe.dto.CommonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GetCommentController(
    private val service: GetCommentService
) {


    /**
     * 댓글 id 로 1건 조회
     */
    @GetMapping("/v1/comments/{commentId}")
    fun getComment(
        @PathVariable commentId: String
    ): CommonResponse<CommentDto> {
        return service.getComment(commentId.toLong())
            .run { CommonResponse(this) }
    }

    /**
     * 댓글 최근 목록 조회
     */
    @GetMapping("/v1/comments/latest")
    fun getLatestCommentList(
        @RequestParam(name = "comment_board_id") boardId: Long,
        @RequestParam(required = false) cursorId: Long,
    ): CommonResponse<List<Comment>> {

        val commentId = getEffectiveCursorId(cursorId)
        return service.getCommentsWithCursorAndBoard(
            GetCommentByBoard(
                boardId = boardId,
                cursorId = commentId
            )
        ).let { CommonResponse(it) }
    }

    /**
     * 특정 게시글 댓글 목록 조회
     */
    @GetMapping("/v1/comments")
    fun getMyCommentList(
        @RequestParam(name = "comment_user_id") userId: Long,
        @RequestParam(name = "comment_board_id") boardId: Long,
        @RequestParam(required = false) cursorId: Long,
    ): CommonResponse<List<Comment>> {

        val commentId = getEffectiveCursorId(cursorId)
        return service.getCommentsWithCursorAndUser(
            GetCommentByUserId(
                boardId = boardId,
                userId = userId,
                cursorId = commentId
            )
        ).let { CommonResponse(it) }
    }

    private fun getEffectiveCursorId(cursorId: Long?): Long {
        return cursorId?.takeIf { it != 0L } ?: Long.MAX_VALUE
    }


}

package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.comment.dto.CommentDto
import lyfe.lyfeBe.comment.CommentGetsByBoard
import lyfe.lyfeBe.comment.CommentGetsByUserId
import lyfe.lyfeBe.comment.service.CommentService
import lyfe.lyfeBe.dto.CommonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GetCommentController(
    private val service: CommentService
) {


    /**
     * 댓글 id 로 1건 조회
     */
    @GetMapping("/v1/comments/{commentId}")
    fun getComment(
        @PathVariable commentId: Long
    ): CommonResponse<CommentDto> {
        return service.getById(commentId)
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
            CommentGetsByBoard(
                boardId = boardId,
                cursorId = commentId
            )
        ).let { CommonResponse(it) }
    }

    /**
     * 자신이 작성한 댓글 조회
     */
    @GetMapping("/v1/comments")
    fun getMyCommentList(
        @RequestParam(name = "comment_user_id") userId: Long,
        @RequestParam(required = false) cursorId: Long,
    ): CommonResponse<List<Comment>> {

        val commentId = getEffectiveCursorId(cursorId)
        return service.getCommentsWithCursorAndUser(
            CommentGetsByUserId(
                userId = userId,
                cursorId = commentId
            )
        ).let { CommonResponse(it) }
    }

    private fun getEffectiveCursorId(cursorId: Long?): Long {
        return cursorId?.takeIf { it != 0L } ?: Long.MAX_VALUE
    }


}

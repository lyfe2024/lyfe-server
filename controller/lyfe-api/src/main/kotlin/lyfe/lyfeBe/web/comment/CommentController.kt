package lyfe.lyfeBe.web.comment

import jakarta.validation.Valid
import lyfe.lyfeBe.comment.*
import lyfe.lyfeBe.comment.dto.CommentDto
import lyfe.lyfeBe.comment.dto.SaveCommentDto
import lyfe.lyfeBe.comment.service.CommentService
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.web.comment.req.SaveCommentRequest
import lyfe.lyfeBe.web.comment.req.UpdateCommentRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
class CommentController(
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
        @PageableDefault(size = 10, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
        ): CommonResponse<List<Comment>> {

        val commentId = getEffectiveCursorId(cursorId)
        return service.getCommentsWithCursorAndBoard(
            CommentGetsByBoard(
                boardId = boardId,
                cursorId = commentId,
                        pageable = pageable
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


    @PostMapping("/v1/comments")
    fun create(
        @Valid @RequestBody req: SaveCommentRequest,
        @RequestParam("comment_board_id") boardId: Long,

        ): CommonResponse<SaveCommentDto> {

        return service.create(
            CommentCreate(
                content = req.content,
                commentGroupId = req.commentGroupId,
                userId = 1L,
                boardId = boardId
            )
        ).let { CommonResponse(it) }

    }

    @PutMapping("/v1/comments/{commentId}")
    fun update(
        @PathVariable commentId: Long,
        @Valid @RequestBody request: UpdateCommentRequest,

        ): CommonResponse<SaveCommentDto> {

        return service.update(
            CommentUpdate(
                commentId = commentId,
                content = request.content,
                userId = 1L
            )
        ).let { CommonResponse(it) }
    }

}
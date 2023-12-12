package lyfe.lyfeBe.web.comment

import jakarta.validation.Valid
import lyfe.lyfeBe.comment.CommentCreate
import lyfe.lyfeBe.comment.dto.SaveCommentDto
import lyfe.lyfeBe.comment.service.CommentService
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.web.comment.req.SaveCommentRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateCommentController(
    private val service: CommentService
) {
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
}
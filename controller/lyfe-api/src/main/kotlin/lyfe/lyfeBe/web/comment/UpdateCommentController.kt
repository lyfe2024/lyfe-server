package lyfe.lyfeBe.web.comment

import jakarta.validation.Valid
import lyfe.lyfeBe.comment.CommentUpdate
import lyfe.lyfeBe.comment.dto.SaveCommentDto
import lyfe.lyfeBe.comment.service.CommentService
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.web.comment.req.UpdateCommentRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UpdateCommentController(
    private val service: CommentService
) {

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
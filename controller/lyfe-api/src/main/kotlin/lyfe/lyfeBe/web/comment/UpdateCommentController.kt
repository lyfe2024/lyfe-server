package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.comment.UpdateComment
import lyfe.lyfeBe.comment.dto.CommentDto
import lyfe.lyfeBe.comment.service.UpdateCommentService
import lyfe.lyfeBe.dto.CommonResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UpdateCommentController(
    private val service: UpdateCommentService
) {

    @PutMapping("/v1/comments/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody request: UpdateCommentRequest,

        ): CommonResponse<CommentDto> {

        return service.update(
            UpdateComment(
                commentId = commentId,
                content = request.content,
                userId = 1L
            )
        ).let { CommonResponse(it) }
    }

}
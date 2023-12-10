package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.comment.port.`in`.UpdateCommentCommand
import lyfe.lyfeBe.comment.service.UpdateCommentService
import lyfe.lyfeBe.dto.CommonResponse
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UpdateCommentController(
    private val service: UpdateCommentService
) {

    @PatchMapping("/v1/comments/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody request: UpdateCommentRequest,

    ): CommonResponse<CommentResponse> {

        return service.update(
            UpdateCommentCommand(
                commentId = commentId,
                content = request.content,
                userId = 1L
            )
        )
            .let { CommentMapper.mapToResponse(it) }
            .let { CommonResponse(it) }
    }

}
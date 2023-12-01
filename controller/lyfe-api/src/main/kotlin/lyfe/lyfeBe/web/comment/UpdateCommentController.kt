package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.comment.port.`in`.UpdateCommentUseCase
import lyfe.lyfeBe.dto.CommonResponse
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UpdateCommentController(
    private val updateCommentUseCase: UpdateCommentUseCase
) {

    @PatchMapping("/v1/comments/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody request: UpdateCommentRequest,
        authentication: Authentication

    ): CommonResponse<CommentResponse> {

        return updateCommentUseCase.update(
            commentId = commentId,
            content = request.content,
            userId = authentication.name.toLong()
        )
            .let { CommentMapper.mapToResponse(it) }
            .let { CommonResponse(it) }
    }

}
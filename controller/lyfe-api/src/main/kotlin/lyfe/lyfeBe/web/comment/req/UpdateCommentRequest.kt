package lyfe.lyfeBe.web.comment.req

import jakarta.validation.constraints.NotBlank

data class UpdateCommentRequest(
    @NotBlank
    val content: String
)

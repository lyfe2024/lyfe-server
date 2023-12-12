package lyfe.lyfeBe.web.comment.req

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.annotation.Nullable
import jakarta.validation.constraints.NotBlank

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SaveCommentRequest(
    @NotBlank
    val content: String,
    @Nullable
    val commentGroupId: Long?
)
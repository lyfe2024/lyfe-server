package lyfe.lyfeBe.web.comment

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreateCommentRequest(
    val content: String,
    val commentGroupId: Long?
)
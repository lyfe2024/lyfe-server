package lyfe.lyfeBe.web.feedback.req

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.constraints.NotBlank

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SaveFeedbackRequest(
    @NotBlank
    val feedback: String
)
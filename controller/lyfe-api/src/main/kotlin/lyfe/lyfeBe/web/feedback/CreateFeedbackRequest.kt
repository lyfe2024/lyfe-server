package lyfe.lyfeBe.web.feedback

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CreateFeedbackRequest(
    @field:NotNull
    val userId : Long,
    @field:NotEmpty
    val feedback: String
)

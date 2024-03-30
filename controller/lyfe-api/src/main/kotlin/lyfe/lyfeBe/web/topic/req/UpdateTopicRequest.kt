package lyfe.lyfeBe.web.topic.req

import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

class UpdateTopicRequest(
    @NotBlank
    val content: String,
    val appliedAt: LocalDate
)
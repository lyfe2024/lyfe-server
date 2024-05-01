package lyfe.lyfeBe.topic

import java.time.LocalDate

data class TopicCreate(
    val content: String,
    val appliedAt : LocalDate?
)

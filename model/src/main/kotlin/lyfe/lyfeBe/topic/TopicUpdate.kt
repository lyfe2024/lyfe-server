package lyfe.lyfeBe.topic

import java.time.LocalDate

data class TopicUpdate(
    val topicId: Long,
    val content: String,
    val appliedAt: LocalDate
)

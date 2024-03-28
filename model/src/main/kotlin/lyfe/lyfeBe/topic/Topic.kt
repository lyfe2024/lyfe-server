package lyfe.lyfeBe.topic

import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Topic(
    val id: Long,
    val content: String,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val appliedAt: String? = null
) {
    companion object {
        fun from(topicCreate: TopicCreate): Topic {
            return Topic(
                id = 0,
                content = topicCreate.content,
                appliedAt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))
            )
        }

        fun from(topicUpdate: TopicUpdate): Topic {
            return Topic(
                id = topicUpdate.topicId,
                content = topicUpdate.content
            )
        }
    }
}

package lyfe.lyfeBe.topic

import java.time.Instant
import java.time.LocalDate

data class Topic(
    val id: Long,
    val content: String,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val appliedAt: LocalDate? = null
) {
    fun update(topicUpdate: TopicUpdate) =
        Topic(
            id = topicUpdate.topicId,
            content = topicUpdate.content,
            createdAt = createdAt,
            updatedAt = Instant.now(),
            appliedAt = topicUpdate.appliedAt
        )

    companion object {
        fun from(topicCreate: TopicCreate): Topic {
            return Topic(
                id = 0,
                content = topicCreate.content,
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
                appliedAt = topicCreate.appliedAt
            )
        }
    }
}

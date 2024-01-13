package lyfe.lyfeBe.topic

import java.time.Instant

data class Topic(
    val id: Long,
    val content: String,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val appliedAt: Instant? = null
) {
    companion object {
        fun from(topicCreate: TopicCreate): Topic {
            return Topic(
                id = 0,
                content = topicCreate.content
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

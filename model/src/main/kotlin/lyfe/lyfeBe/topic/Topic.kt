package lyfe.lyfeBe.topic

import java.time.Instant

data class Topic(
    val id: Long,
    val content: String,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val visibility: Boolean,
) {
    companion object {
        fun from(topicCreate: TopicCreate): Topic {
            return Topic(
                id = 0,
                content = topicCreate.content,
                visibility = true
            )
        }

        fun from(topicCreate: TopicUpdate): Topic {
            return Topic(
                id = topicCreate.topicId,
                content = topicCreate.content,
                visibility = true
            )
        }
    }
}

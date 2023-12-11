package lyfe.lyfeBe.topic.dto

import lyfe.lyfeBe.topic.Topic

class GetTopicDto(
    val id: Long,
    val content: String,
    val createdAt: String
) {

    companion object {
        fun toDto(topic: Topic) =
            GetTopicDto(
                id = topic.id,
                content = topic.content,
                createdAt = topic.createdAt.toString()
            )

    }
}
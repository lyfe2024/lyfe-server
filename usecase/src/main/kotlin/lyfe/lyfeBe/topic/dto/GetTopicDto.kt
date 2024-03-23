package lyfe.lyfeBe.topic.dto

import lyfe.lyfeBe.topic.Topic

class GetTopicDto(
    val id: Long,
    val content: String,
    val appliedAt: String?

) {

    companion object {
        fun toDto(topic: Topic) =
            GetTopicDto(
                id = topic.id,
                content = topic.content,
                appliedAt = topic.appliedAt
            )

        fun toDtoList(topics: List<Topic>): List<GetTopicDto> =
            topics.map { toDto(it) }
    }
}
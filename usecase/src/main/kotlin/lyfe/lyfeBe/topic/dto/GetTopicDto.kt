package lyfe.lyfeBe.topic.dto

import lyfe.lyfeBe.topic.Topic

class GetTopicDto(
    val id: Long,
    val content: String
) {

    companion object {
        fun toDto(topic: Topic) =
            GetTopicDto(
                id = topic.id,
                content = topic.content
            )

        fun toDtoList(topics: List<Topic>): List<GetTopicDto> =
            topics.map { toDto(it) }
    }
}
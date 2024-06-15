package lyfe.lyfeBe.topic.dto

import lyfe.lyfeBe.topic.Topic

data class TopicDto(
    val id: Long,
    val content: String,
    val date: String?
) {

    companion object {
        fun from(topic: Topic): TopicDto {
            return TopicDto(
                id = topic.id,
                content = topic.content,
                date = topic.appliedAt.toString()
            )
        }
    }
}
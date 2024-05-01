package lyfe.lyfeBe.topic.dto

import lyfe.lyfeBe.topic.Topic
import java.time.LocalDate

class TopicDto(
    val id: Long,
    val content: String,
    val date: LocalDate?
) {

    companion object {
        fun from(topic: Topic): TopicDto {
            return TopicDto(
                id = topic.id,
                content = topic.content,
                date = topic.appliedAt
            )
        }
    }
}
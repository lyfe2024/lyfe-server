package lyfe.lyfeBe.topic.dto

import lyfe.lyfeBe.topic.Topic

class SaveTopicDto(
    val id: Long,
) {

    companion object {
        fun toDto(topic: Topic)  = SaveTopicDto(id = topic.id)
    }
}
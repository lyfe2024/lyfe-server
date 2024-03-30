package lyfe.lyfeBe.topic.dto

import lyfe.lyfeBe.topic.Topic

class SaveTopicDto(
    val id: Long,
) {

    companion object {
        fun from(topic: Topic): SaveTopicDto {
            return SaveTopicDto(
                id = topic.id,
            )
        }
    }
}
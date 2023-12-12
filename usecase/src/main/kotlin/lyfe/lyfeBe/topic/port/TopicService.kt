package lyfe.lyfeBe.topic.port

import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.topic.TopicCreate
import lyfe.lyfeBe.topic.TopicGet
import lyfe.lyfeBe.topic.TopicUpdate
import lyfe.lyfeBe.topic.dto.GetTopicDto
import lyfe.lyfeBe.topic.dto.SaveTopicDto
import org.springframework.stereotype.Service

@Service
class TopicService(
    private val topicPort: TopicPort,
) {

    fun create(topicCreate: TopicCreate) = SaveTopicDto.toDto(topicPort.create(Topic.from(topicCreate)))

    fun update(topicUpdate: TopicUpdate) {
        topicPort.update(Topic.from(topicUpdate))

    }

    fun get(topicGet: TopicGet) =
        GetTopicDto.toDto(topicPort.getById(topicGet.topicId))

}
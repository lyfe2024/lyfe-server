package lyfe.lyfeBe.topic.port

import lyfe.lyfeBe.Constants.Companion.CURSOR_VALUE
import lyfe.lyfeBe.topic.*
import lyfe.lyfeBe.topic.dto.GetTopicDto
import lyfe.lyfeBe.topic.dto.SaveTopicDto
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    fun getPast(topicPastGet: TopicPastGet): List<GetTopicDto> {
        val topics = topicPort.getPast(
            topicPastGet.date,
            CURSOR_VALUE,
            topicPastGet.pageable
        ).toList()
        return GetTopicDto.toDtoList(topics)

    }

    fun getToday() = GetTopicDto.toDto(topicPort.getToday())
}
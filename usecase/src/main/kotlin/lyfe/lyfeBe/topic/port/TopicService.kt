package lyfe.lyfeBe.topic.port

import lyfe.lyfeBe.auth.service.SecurityUtils.getLoginUser
import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.topic.*
import lyfe.lyfeBe.topic.dto.SaveTopicDto
import lyfe.lyfeBe.topic.dto.TopicDto
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TopicService(
    private val topicPort: TopicPort,
    private val userPort: UserPort
) {
    @Transactional
    fun create(topicCreate: TopicCreate) : SaveTopicDto{
        val user = getLoginUser(userPort)
        if(user.role != Role.ADMIN) throw ResourceNotFoundException("관리자만 접근 가능합니다.")

        return SaveTopicDto(topicPort.create(Topic.from(topicCreate)).id)
    }

    @Transactional
    fun update(topicUpdate: TopicUpdate) : SaveTopicDto {
        val user = getLoginUser(userPort)
        if(user.role != Role.ADMIN) throw ResourceNotFoundException("관리자만 접근 가능합니다.")

        val topic = topicPort.getById(id = topicUpdate.topicId).update(topicUpdate)
        return SaveTopicDto(topicPort.update(topic).id)
    }

    fun get(topicGet: TopicGet): TopicDto {
        return topicPort.getById(id = topicGet.topicId)
            .run { TopicDto.from(this) }
    }

    fun getToday(): TopicDto {
        return topicPort.getToday()
            .run { TopicDto.from(this) }
    }

    fun getPast(topicPastGet: TopicPastGet): TopicDto {
        return topicPort.getDate(date = topicPastGet.date)
            .run { TopicDto.from(this) }
    }
}


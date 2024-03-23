package lyfe.lyfeBe.persistence.topic

import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.fomatter.DateConverter
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.topic.port.TopicPort
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class TopicPersistenceAdapter(
    private val topicRepository: TopicJpaRepository
) : TopicPort {

    override fun create(topic: Topic) = topicRepository.save(TopicJpaEntity.from(topic)).toDomain()

    override fun getById(topicId: Long): Topic {
        return topicRepository.findById(topicId)
            .orElseThrow { ResourceNotFoundException("topic not found") }
            .toDomain()
    }

    override fun update(from: Topic) {
        topicRepository.save(TopicJpaEntity.from(from))
    }

    override fun getPast(date: String, cursorId: Long, pageable: Pageable) =
        topicRepository.findPastTopics(
            DateConverter.toInstant(date),
            cursorId,
            pageable
        ).map { it.toDomain() }

    override fun getToday() : Topic{
        val tomorrow = LocalDate.now()
        return topicRepository.findByDate(tomorrow)
            .map { it.toDomain() }
            .orElseThrow { IllegalStateException("Topic not found for the given date") }
    }
}
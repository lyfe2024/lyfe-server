package lyfe.lyfeBe.persistence.topic

import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.topic.port.TopicPort
import org.springframework.stereotype.Component

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
}
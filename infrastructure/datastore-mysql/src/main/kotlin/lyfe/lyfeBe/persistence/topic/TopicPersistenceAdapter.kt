package lyfe.lyfeBe.persistence.topic

import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.topic.port.TopicPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class TopicPersistenceAdapter(
    private val topicRepository: TopicJpaRepository
) : TopicPort {
    override fun getById(topicId: Long): Topic {
        return topicRepository.findById(topicId)
            .orElseThrow { RuntimeException("topic not found") }
            .toDomain()
    }
}
package lyfe.lyfeBe.persistence.topic

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class TopicPersistenceAdapter(
    private val topicRepository: lyfe.lyfeBe.persistence.topic.TopicRepository
) {
}
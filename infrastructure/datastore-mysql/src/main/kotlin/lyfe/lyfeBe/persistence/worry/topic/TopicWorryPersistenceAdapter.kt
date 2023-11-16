package lyfe.lyfeBe.persistence.worry.topic

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class TopicWorryPersistenceAdapter(
    private val topicWorryRepository: lyfe.lyfeBe.persistence.worry.topic.TopicWorryRepository
) {
}
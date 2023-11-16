package lyfe.lyfeBe.persistence.picture.topic

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class TopicPicturePersistenceAdapter(
    private val topicPictureRepository: lyfe.lyfeBe.persistence.picture.topic.TopicPictureRepository
) {
}
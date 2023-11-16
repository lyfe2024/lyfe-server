package lyfe.lyfeBe.persistence.feedback

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class FeedbackPersistenceAdapter(
    private val feedbackRepository: lyfe.lyfeBe.persistence.feedback.FeedbackRepository
) {

}
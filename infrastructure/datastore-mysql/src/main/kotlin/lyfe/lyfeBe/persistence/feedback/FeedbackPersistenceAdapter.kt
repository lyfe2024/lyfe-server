package lyfe.lyfeBe.persistence.feedback

import lyfe.lyfeBe.feedback.Feedback
import lyfe.lyfeBe.feedback.port.out.FeedBackPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FeedbackPersistenceAdapter(
    private val feedbackRepository: FeedbackRepository

) : FeedBackPort {
    override fun create(feedBack: Feedback) =
        feedbackRepository.save(FeedbackJpaEntity.from(feedBack)).toDomain()

    override fun getById(id: Long): Feedback {
        TODO("Not yet implemented")
    }

}
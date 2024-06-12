package lyfe.lyfeBe.persistence.feedback

import lyfe.lyfeBe.feedback.Feedback
import lyfe.lyfeBe.feedback.port.out.FeedbackPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class FeedbackPersistenceAdapter(
    private val feedbackRepository: FeedbackRepository
): FeedbackPort {

    @Transactional
    override fun create(feedback: Feedback): Feedback {
        check(feedback.id == 0L) { "ID가 0 or null이 아니면 생성할 수 없습니다." }
        return feedbackRepository.save(FeedbackJpaEntity.from(feedback)).toDomain()
    }

}
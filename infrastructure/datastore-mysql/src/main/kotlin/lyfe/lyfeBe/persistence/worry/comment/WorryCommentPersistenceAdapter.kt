package lyfe.lyfeBe.persistence.worry.comment

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class WorryCommentPersistenceAdapter(
    private val worryCommentRepository: lyfe.lyfeBe.persistence.worry.comment.WorryCommentRepository
) {
}
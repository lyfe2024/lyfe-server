package lyfe.lyfeBe.persistence.comment

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class CommentPersistenceAdapter(
    private val commentRepository: CommentRepository
) {
}
package lyfe.lyfeBe.persistence.picture.comment

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class PictureCommentPersistenceAdapter(
    private val pictureCommentRepository: lyfe.lyfeBe.persistence.picture.comment.PictureCommentRepository
) {
}
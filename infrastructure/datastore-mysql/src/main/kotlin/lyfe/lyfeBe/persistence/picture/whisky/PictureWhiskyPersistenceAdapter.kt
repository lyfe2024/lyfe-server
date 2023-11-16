package lyfe.lyfeBe.persistence.picture.whisky

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class PictureWhiskyPersistenceAdapter(
    private val pictureWhiskyRepository: lyfe.lyfeBe.persistence.picture.whisky.PictureWhiskyRepository
) {
}
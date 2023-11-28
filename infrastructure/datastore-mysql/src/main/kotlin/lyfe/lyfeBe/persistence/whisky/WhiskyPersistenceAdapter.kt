package lyfe.lyfeBe.persistence.whisky

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class WhiskyPersistenceAdapter(
    private val whiskyRepository: WhiskyRepository
) {
}
package lyfe.lyfeBe.persistence.notification

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class NotificationHistoryPersistenceAdapter(
    private val notificationHistoryRepository: NotificationHistoryRepository
) {
}
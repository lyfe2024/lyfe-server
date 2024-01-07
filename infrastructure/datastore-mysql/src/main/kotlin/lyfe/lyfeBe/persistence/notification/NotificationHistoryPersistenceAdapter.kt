package lyfe.lyfeBe.persistence.notification

import lyfe.lyfeBe.fcm.port.FcmPort
import lyfe.lyfeBe.notification.NotificationHistory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class NotificationHistoryPersistenceAdapter(
    private val notificationHistoryRepository: NotificationHistoryRepository
) : FcmPort {

    override fun findByIdCursorId(cursorId: Long, paging: Pageable) =
        notificationHistoryRepository.findByIdCursorId(cursorId, paging).map {
            it.toDomain()
        }

    override fun save(notificationHistory: NotificationHistory): NotificationHistory =
        notificationHistoryRepository.save(NotificationHistoryJpaEntity.from(notificationHistory)).toDomain()
}
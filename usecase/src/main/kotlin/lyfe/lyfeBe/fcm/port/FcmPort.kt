package lyfe.lyfeBe.fcm.port

import lyfe.lyfeBe.notification.NotificationHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FcmPort {
    fun findByIdCursorId(cursorId: Long, paging: Pageable): Page<NotificationHistory>
    fun save(notificationHistory: NotificationHistory): NotificationHistory
}
package lyfe.lyfeBe.notification

import org.springframework.data.domain.Pageable


data class NotificationGet(
        val notificationId: Long , val pageable: Pageable
)
package lyfe.lyfeBe.web.notification

import lyfe.lyfeBe.notification.NotificationType

data class NotificationResponse(
    val id: Long,
    val notificationTargetId : Long,
    val notificationType: NotificationType,
    val content: String,
    val notifiedAt: String,
)

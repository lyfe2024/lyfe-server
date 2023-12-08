package lyfe.lyfeBe.web.notification

import lyfe.lyfeBe.notification.NotificationType

data class NotificationResponse(
    val notificationType: NotificationType,
    val content: String,
    val notifiedAt: String,
)

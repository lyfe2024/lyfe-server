package lyfe.lyfeBe.fcm.req

import lyfe.lyfeBe.notification.NotificationContent
import lyfe.lyfeBe.notification.NotificationType

data class NotificationRequest(
    val token: String,
    val notificationType: NotificationType,
    val notificationContent: NotificationContent
)

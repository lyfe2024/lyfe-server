package lyfe.lyfeBe.notification


data class NotificationSend(
        val userId: Long,
        val token: String,
        val type: NotificationType,
        val content: NotificationContent
)

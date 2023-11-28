package lyfe.lyfeBe.notification

import lyfe.lyfeBe.user.User
import java.time.Instant

data class NotificationHistory(
    val id: Long,
    val content: String,
    val notificationType: NotificationType,
    val user: User,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val visibility: Boolean,
)

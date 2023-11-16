package lyfe.lyfeBe.notification.domain

import lyfe.lyfeBe.user.domain.User
import java.time.Instant

data class NotificationHistory(
    val id: Long,
    val content: String,
    val notificationType: String,
    val notifiedAt: Instant,
    val user: User,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val visibility: Boolean,
)

package lyfe.lyfeBe.notification

import lyfe.lyfeBe.user.User
import java.time.Instant

data class NotificationHistory(
    val id: Long,
    val content: String,
    val notificationType: NotificationType,
    val user: User,
    val createdAt: Instant?,
    val updatedAt: Instant?
) {
    companion object {
        fun from(send: NotificationSend, user: User) =
            NotificationHistory(
                id = 0,
                content = send.content.description,
                notificationType = send.type,
                user = user,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
    }
}
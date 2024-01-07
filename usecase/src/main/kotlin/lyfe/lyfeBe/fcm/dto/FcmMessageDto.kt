import lyfe.lyfeBe.notification.NotificationHistory
import lyfe.lyfeBe.notification.NotificationType
import lyfe.lyfeBe.user.User
import java.time.Instant

data class FcmMessageDto(
    val id: Long,
    val content: String,
    val notificationType: NotificationType,
    val user: User,
    val createdAt: Instant?,
    val updatedAt: Instant?
) {
    companion object {
        fun toDto(notification: NotificationHistory): FcmMessageDto {
            return FcmMessageDto(
                id = notification.id,
                content = notification.content,
                notificationType = notification.notificationType,
                user = notification.user,
                createdAt = notification.createdAt,
                updatedAt = notification.updatedAt
            )
        }
        fun toDtos(notifications: List<NotificationHistory>): List<FcmMessageDto> {
            return notifications.map {
                FcmMessageDto(
                    id = it.id,
                    content = it.content,
                    notificationType = it.notificationType,
                    user = it.user,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt
                )
            }
        }
    }
}

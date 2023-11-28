package lyfe.lyfeBe.persistence.notification

import lyfe.lyfeBe.notification.NotificationHistory
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.user.UserMapper

object NotificationHistoryMapper {

    fun mapToDomainEntity(notificationHistory: NotificationHistoryJpaEntity): NotificationHistory =
        NotificationHistory(
            id = notificationHistory.id,
            content = notificationHistory.content,
            notificationType = notificationHistory.notificationType,
            user = UserMapper.mapToDomainEntity(notificationHistory.user),
            createdAt = notificationHistory.baseEntity.createdAt,
            updatedAt = notificationHistory.baseEntity.updatedAt,
            visibility = notificationHistory.baseEntity.visibility,
        )

    fun mapToJpaEntity(notificationHistory: NotificationHistory): NotificationHistoryJpaEntity =
        NotificationHistoryJpaEntity(
            id = notificationHistory.id,
            content = notificationHistory.content,
            notificationType = notificationHistory.notificationType,
            user = UserMapper.mapToJpaEntity(notificationHistory.user),
            baseEntity = BaseEntity(
                createdAt = notificationHistory.createdAt,
                updatedAt = notificationHistory.updatedAt,
                visibility = notificationHistory.visibility
            )
        )
}
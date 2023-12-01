package lyfe.lyfeBe.persistence.user

import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.user.User

object UserMapper {

    fun mapToDomainEntity(user: UserJpaEntity): User =
        User(
            id = user.id,
            email = user.email,
            hashedPassword = user.hashedPassword,
            nickname = user.nickname,
            notificationConsent = user.notificationConsent,
            fcmRegistration = user.fcmRegistration,
            role = user.role,
            userStatus = user.userStatus,
            createdAt = user.baseEntity.createdAt,
            updatedAt = user.baseEntity.updatedAt,
            withdrawnAt = user.withdrawnAt,
            visibility = user.baseEntity.visibility,
        )

    fun mapToJpaEntity(user: User): UserJpaEntity =
        UserJpaEntity(
            id = user.id,
            email = user.email,
            hashedPassword = user.hashedPassword,
            nickname = user.nickname,
            notificationConsent = user.notificationConsent,
            fcmRegistration = user.fcmRegistration,
            withdrawnAt = user.withdrawnAt,
            role = user.role,
            userStatus = user.userStatus,
            baseEntity = BaseEntity(
                createdAt = user.createdAt,
                updatedAt = user.updatedAt,
                visibility = user.visibility == true //FIXME: 여긴어떻게처리..?
            )
        )
}
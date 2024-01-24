package lyfe.lyfeBe.persistence.user

import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.user.User

object UserMapper {


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
            profileUrl = user.profileUrl,
            userStatus = user.userStatus,
            baseEntity = BaseEntity(
                createdAt = user.createdAt,
                updatedAt = user.updatedAt
            )
        )
}
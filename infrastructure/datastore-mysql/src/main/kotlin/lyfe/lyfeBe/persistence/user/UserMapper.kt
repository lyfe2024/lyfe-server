package lyfe.lyfeBe.persistence.user

import lyfe.lyfeBe.user.domain.User

object UserMapper {

    fun mapToDomainEntity(user: UserJpaEntity): User =
        User(
            id = user.id,
            email = user.email,
            hashedPassword = user.hashedPassword,
            nickname = user.nickname,
            notificationConsent = user.notificationConsent,
            fcmRegistration = user.fcmRegistration,
            profileImage = user.profileImage,
            withdrawnAt = user.withdrawnAt,
            role = user.role,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
            visibility = user.visibility,
        )

    fun mapToJpaEntity(user: User): UserJpaEntity =
        UserJpaEntity(
            id = user.id,
            email = user.email,
            hashedPassword = user.hashedPassword,
            nickname = user.nickname,
            notificationConsent = user.notificationConsent,
            fcmRegistration = user.fcmRegistration,
            profileImage = user.profileImage,
            withdrawnAt = user.withdrawnAt,
            role = user.role,
        ).apply {
            createdAt = user.createdAt
            updatedAt = user.updatedAt
            visibility = user.visibility
        }
}
package lyfe.lyfeBe.persistence.user

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
            profileImage = user.profileImage,
            role = user.role,
            userStatus = user.userStatus,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
            withdrawnAt = user.withdrawnAt,
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
            userStatus = user.userStatus,
        ).apply {
            createdAt = user.createdAt
            updatedAt = user.updatedAt
            visibility = user.visibility
        }
}
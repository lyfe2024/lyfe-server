package lyfe.lyfeBe.user

import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.error.ResourceNotFoundException
import java.time.Instant

data class User(
    val id: Long,
    val email: String,
    val hashedPassword: String,
    val nickname: String,
    val socialId: String,
    val socialType: SocialType,
    val socialRefreshToken: String? = null,
    val notificationConsent: Boolean,
    val fcmRegistration: Boolean,
    val role: Role,
    val userStatus: UserStatus,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val withdrawnAt: Instant? = null,
    val visibility: Boolean,
) {
    fun validateActive() {
        if (this.withdrawnAt != null) {
            throw ResourceNotFoundException("탈퇴한 유저입니다.")
        }
    }

    fun updateNickName(nickname: String) =
        User(
            id = id,
            email = email,
            hashedPassword = hashedPassword,
            nickname = nickname,
            socialId = socialId,
            socialType = socialType,
            socialRefreshToken = socialRefreshToken,
            notificationConsent = notificationConsent,
            fcmRegistration = fcmRegistration,
            role = role,
            userStatus = userStatus,
            createdAt = createdAt,
            updatedAt = Instant.now(),
            withdrawnAt = withdrawnAt,
            visibility = visibility
        )

    companion object {
        fun from(userCreate: UserCreate) =
            User(
                id = 0,
                email = userCreate.email,
                hashedPassword = userCreate.email + "lyfe",
                nickname = userCreate.nickname,
                socialId = userCreate.socialId,
                socialType = userCreate.socialType,
                socialRefreshToken = null,
                notificationConsent = false,
                fcmRegistration = false,
                role = userCreate.role,
                userStatus = UserStatus.ACTIVE,
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
                withdrawnAt = null,
                visibility = true
            )

        fun update(userJoin: UserJoin, user: User) =
            User(
                id = user.id,
                email = user.email,
                hashedPassword = user.hashedPassword,
                nickname = userJoin.nickname,
                socialId = user.socialId,
                socialType = user.socialType,
                socialRefreshToken = user.socialRefreshToken,
                notificationConsent = user.notificationConsent,
                fcmRegistration = user.fcmRegistration,
                role = user.role,
                userStatus = user.userStatus,
                createdAt = user.createdAt,
                updatedAt = Instant.now(),
                withdrawnAt = user.withdrawnAt,
                visibility = user.visibility
            )
    }
}

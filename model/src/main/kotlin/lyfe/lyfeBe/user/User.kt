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
    val profileUrl : String,
    val userStatus: UserStatus,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val withdrawnAt: Instant? = null
){
    fun validateActive() {
        if (this.withdrawnAt != null) {
            throw ResourceNotFoundException("탈퇴한 유저입니다.")
        }
    }

    fun withdraw() =
        User(
            id = id,
            email = "",
            hashedPassword = "",
            nickname = "탈퇴한 유저",
            socialId = "",
            socialType = socialType,
            socialRefreshToken = "",
            profileUrl = "",
            notificationConsent = notificationConsent,
            fcmRegistration = fcmRegistration,
            role = Role.GUEST,
            userStatus = UserStatus.WITHDRAWN,
            createdAt = createdAt,
            updatedAt = Instant.now(),
            withdrawnAt = Instant.now(),
        )

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
            profileUrl = profileUrl,
            withdrawnAt = withdrawnAt,
        )

    companion object {
        fun from(
            userJoin: UserJoin,
            userCredentials: Pair<String, String>,
            socialRefreshToken: String?
        ): User{
            val email = userCredentials.first
            val hashedPassword = userCredentials.second
            return User(
                id = 0,
                email = email,
                hashedPassword = hashedPassword,
                nickname = userJoin.nickname,
                socialId = email.split("@")[0],
                socialType = SocialType.valueOf(email.split("@")[1]),
                socialRefreshToken = socialRefreshToken,
                notificationConsent = false,
                fcmRegistration = false,
                role = Role.USER,
                userStatus = UserStatus.ACTIVE,
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
                profileUrl = "", //Todo 이부분처리 필요
                withdrawnAt = null,
            )
        }

        fun from(user: User): User {
            return User(
                id = user.id,
                email = user.email,
                hashedPassword = user.hashedPassword,
                nickname = user.nickname,
                socialId = user.socialId,
                socialType = user.socialType,
                socialRefreshToken = user.socialRefreshToken,
                notificationConsent = user.notificationConsent,
                fcmRegistration = user.fcmRegistration,
                profileUrl = user.profileUrl,
                role = user.role,
                userStatus = user.userStatus,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt,
                withdrawnAt = user.withdrawnAt
            )
        }
    }
}

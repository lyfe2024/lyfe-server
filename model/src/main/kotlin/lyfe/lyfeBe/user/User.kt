package lyfe.lyfeBe.user

import lyfe.lyfeBe.error.ResourceNotFoundException
import java.time.Instant

data class User(
    val id: Long,
    val email: String,
    val hashedPassword: String,
    val nickname: String,
    val notificationConsent: Boolean,
    val fcmRegistration: Boolean,
    val role: Role,
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

    companion object {
        fun from(user: User): User {
            return User(
                id = user.id,
                email = user.email,
                hashedPassword = user.hashedPassword,
                nickname = user.nickname,
                notificationConsent = user.notificationConsent,
                fcmRegistration = user.fcmRegistration,
                role = user.role,
                userStatus = user.userStatus,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt,
                withdrawnAt = user.withdrawnAt
            )
        }
    }
}

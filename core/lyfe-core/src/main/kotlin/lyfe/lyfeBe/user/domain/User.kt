package lyfe.lyfeBe.user.domain

import lyfe.lyfeBe.error.ResourceNotFoundException
import java.time.Instant

data class User(
    val id: Long,
    val email: String,
    val hashedPassword: String,
    val nickname: String,
    val notificationConsent: Boolean,
    val fcmRegistration: Boolean,
    val profileImage: String?,
    val role: Role,
    val userStatus: UserStatus,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val withdrawnAt: Instant?,
    val visibility: Boolean,
){
    fun validateActive() {
        if (this.withdrawnAt != null) {
            throw ResourceNotFoundException("탈퇴한 유저입니다.")
        }
    }
}

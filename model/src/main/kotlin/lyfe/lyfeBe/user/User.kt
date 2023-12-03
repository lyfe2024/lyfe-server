package lyfe.lyfeBe.user

import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.image.Image
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
    val withdrawnAt: Instant? = null,
    val visibility: Boolean? = null,
){
    fun validateActive() {
        if (this.withdrawnAt != null) {
            throw ResourceNotFoundException("탈퇴한 유저입니다.")
        }
    }
}

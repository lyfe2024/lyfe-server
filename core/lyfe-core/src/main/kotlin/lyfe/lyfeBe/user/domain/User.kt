package lyfe.lyfeBe.user.domain

import java.time.Instant

data class User(
    val id: Long,
    val email: String,
    val hashedPassword: String,
    val nickname: String,
    val notificationConsent: Boolean,
    val fcmRegistration: Boolean,
    val profileImage: String?,
    val withdrawnAt: Instant?,
    val role: Role,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val visibility: Boolean,
)

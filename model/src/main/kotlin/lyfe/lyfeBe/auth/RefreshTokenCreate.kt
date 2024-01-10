package lyfe.lyfeBe.auth

import lyfe.lyfeBe.user.User


data class RefreshTokenCreate(
    val userId: Long,
    val refreshToken: String,
    val user: User,
)

package lyfe.lyfeBe.auth

import lyfe.lyfeBe.user.User


data class RefreshTokenCreate(
    val refreshToken: String,
    val user: User,
)

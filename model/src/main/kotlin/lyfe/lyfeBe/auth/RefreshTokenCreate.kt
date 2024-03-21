package lyfe.lyfeBe.auth

import lyfe.lyfeBe.user.User
import java.time.Instant


data class RefreshTokenCreate(
    val refreshToken: String,
    val user: User,
    val expiredAt: Instant,
    val tokenStatus: TokenStatus
)

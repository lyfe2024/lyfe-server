package lyfe.lyfeBe.auth

import lyfe.lyfeBe.user.User
import java.time.Instant

data class RefreshToken(
    val id : Long,
    val refreshToken: String,
    val user: User,
    val expiredAt: Instant,
    val tokenStatus: TokenStatus
){

    companion object {
        fun from(refreshTokenCreate: RefreshTokenCreate) =
            RefreshToken(
                id = 0,
                refreshToken = refreshTokenCreate.refreshToken,
                user = refreshTokenCreate.user,
                expiredAt = refreshTokenCreate.expiredAt,
                tokenStatus = refreshTokenCreate.tokenStatus
            )
    }
}

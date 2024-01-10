package lyfe.lyfeBe.auth

import lyfe.lyfeBe.user.User

data class RefreshToken(
    val id : Long,
    val refreshToken: String,
    val user: User,
){

    companion object {
        fun from(refreshTokenCreate: RefreshTokenCreate) =
            RefreshToken(
                id = refreshTokenCreate.userId,
                refreshToken = refreshTokenCreate.refreshToken,
                user = refreshTokenCreate.user,
            )
    }
}

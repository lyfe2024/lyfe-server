package lyfe.lyfeBe.auth

import lyfe.lyfeBe.user.User

data class RefreshToken(
    val id : Long,
    val refreshToken: String,
    val user: User,
){

    companion object {

        fun from(refreshTokenCreate: RefreshTokenCreate, user: User) =
            RefreshToken(
                id = 0,
                refreshToken = refreshTokenCreate.refreshToken,
                user = user
            )
    }
}

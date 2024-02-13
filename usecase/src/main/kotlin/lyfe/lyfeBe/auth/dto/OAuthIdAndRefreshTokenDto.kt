package lyfe.lyfeBe.auth.dto

data class OAuthIdAndRefreshTokenDto(
    val oAuthId: String,
    val refreshToken: String
)

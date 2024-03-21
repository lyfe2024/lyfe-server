package lyfe.lyfeBe.auth.dto

data class TokenDto(
    val accessToken: String,
    val refreshToken: String
)

data class SingleTokenDto(
    val token: String
)
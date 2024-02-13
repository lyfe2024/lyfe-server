package lyfe.lyfeBe.auth.dto.apple

data class AppleLoginRequest(
    val identityToken: String,
    val authorizationCode: String,
)

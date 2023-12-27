package lyfe.lyfeBe.auth

data class AuthLogin(
    val socialType: SocialType,
    val authorizationCode: String,
    val identityToken: String?,
    val fcmToken: String?,
)

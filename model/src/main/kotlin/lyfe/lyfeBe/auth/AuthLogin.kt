package lyfe.lyfeBe.auth

data class AuthLogin(
    val socialType: SocialType,
    val authorizationCode: String?,
    val idToken: String?,
    val fcmToken: String?,
)

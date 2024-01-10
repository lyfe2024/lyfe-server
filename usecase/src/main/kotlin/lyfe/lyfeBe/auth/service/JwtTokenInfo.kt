package lyfe.lyfeBe.auth.service

object JwtTokenInfo {
    const val AUTHORIZATION_HEADER = "Authorization"
    const val BEARER_TYPE = "Bearer"
    const val SIGN_UP_SUBJECT: String = "SignUp"
    const val EMAIL_CLAIM: String = "email"
    const val ACCESS_TOKEN: String = "AccessToken"
    const val REFRESH_TOKEN: String = "RefreshToken"
    const val TEN_MINUTE = 3600000
}
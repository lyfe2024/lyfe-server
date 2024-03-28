package lyfe.lyfeBe.auth.service

object JwtTokenInfo {
    const val AUTHORIZATION_HEADER = "Authorization"
    const val BEARER_TYPE = "Bearer"
    const val SIGN_UP_SUBJECT: String = "SignUp"
    const val EMAIL_CLAIM: String = "email"
    const val ACCESS_TOKEN: String = "AccessToken"
    const val REFRESH_TOKEN: String = "RefreshToken"
    const val PERMANENT_TOKEN: String = "PermanentToken"
    const val TEN_MINUTE = 3600000
    const val PERMANENT_TOKEN_EXPIRE_TIME = 3153600000000 // 1000 * 60 * 60 * 24 * 365 * 100
}
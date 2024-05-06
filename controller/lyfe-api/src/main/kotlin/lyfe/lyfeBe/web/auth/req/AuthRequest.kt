package lyfe.lyfeBe.web.auth.req

import lyfe.lyfeBe.auth.SocialType

data class AuthRequest(
    val socialType: SocialType,
    val authorizationCode: String,
    val idToken: String?,
    val fcmToken: String?,
)

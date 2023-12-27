package lyfe.lyfeBe.web.auth.req

import lyfe.lyfeBe.auth.SocialType

data class AuthRequest(
    val socialType: SocialType,
    val authorizationCode: String,
    val identityToken: String?,
    val fcmToken: String?,
)

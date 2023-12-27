package lyfe.lyfeBe.user

import lyfe.lyfeBe.auth.SocialType

data class UserCreate(
    val email: String,
    val nickname: String,
    val socialId: String,
    val socialType: SocialType,
    val socialRefreshToken: String,
    val role: Role,
)
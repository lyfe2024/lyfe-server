package lyfe.lyfeBe.auth.service

import lyfe.lyfeBe.auth.AuthLogin
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.auth.dto.OAuthIdAndRefreshTokenDto

interface AuthProviderService {
    fun fetchAuthToken(authLoginRequest: AuthLogin): OAuthIdAndRefreshTokenDto
    infix fun isSupport(socialType: SocialType): Boolean
    fun revoke(socialId : String, socialRefreshToken : String?): Boolean

}
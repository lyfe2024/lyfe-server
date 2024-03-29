package lyfe.lyfeBe.auth.service.kakao

import lyfe.lyfeBe.auth.AuthLogin
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.auth.dto.OAuthIdAndRefreshTokenDto
import lyfe.lyfeBe.auth.dto.kakao.KakaoTokenResult
import lyfe.lyfeBe.auth.service.AuthProviderService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.util.UriUtils

@Service
class KakaoService(
    private val kakaoClient: KakaoClient,
    @Value("\${kakao.kakaoClientId}") private var kakaoClientId: String,
    @Value("\${kakao.kakaoRedirectUri}") private var kakaoRedirectUri: String,
    @Value("\${kakao.kakaoGrantType}") private var kakaoGrantType: String,
    @Value("\${kakao.adminKey}") private var adminKey: String
): AuthProviderService {
    fun getAuthorizationCode(): String {
        return kakaoClient.getAuthorizationCode(
            clientId = kakaoClientId, redirectUri = kakaoRedirectUri, responseType = "code"
        )
    }

    override fun fetchAuthToken(authLoginRequest: AuthLogin): OAuthIdAndRefreshTokenDto {
        val kakaoId = getKakaoId(authLoginRequest.idToken?: "")

        return OAuthIdAndRefreshTokenDto(
            oAuthId = kakaoId, refreshToken = "not supported"
        )
    }

    override fun isSupport(socialType: SocialType): Boolean {
        return socialType == SocialType.KAKAO
    }

    override fun revoke(socialId: String, socialRefreshToken: String?): Boolean {
        val unlinkResponse = kakaoClient.unlink(
            adminKey = "KakaoAK $adminKey",
            targetIdType = "user_id",
            targetId = socialId
        )

        require(unlinkResponse.id.toString() != socialId)
        return true
    }

    fun generateAuthToken(authorizationCode: String): KakaoTokenResult {
            return kakaoClient.getToken(
                grantType = kakaoGrantType,
                clientId = kakaoClientId,
                redirectUri = UriUtils.decode(kakaoRedirectUri, "UTF-8"),
                code = authorizationCode,
            )
    }

    fun getKakaoId(accessToken: String): String {
        val kakaoUserInfo = kakaoClient.getUserInfo(accessToken)
        return kakaoUserInfo.id.toString()
    }

}
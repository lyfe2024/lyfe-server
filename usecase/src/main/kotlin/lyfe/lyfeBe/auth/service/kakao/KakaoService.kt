package lyfe.lyfeBe.auth.service.kakao

import lyfe.lyfeBe.auth.AuthLogin
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.auth.dto.OAuthIdAndRefreshTokenDto
import lyfe.lyfeBe.auth.dto.kakao.KakaoTokenRequest
import lyfe.lyfeBe.auth.dto.kakao.KakaoTokenResult
import lyfe.lyfeBe.auth.service.AuthProviderService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class KakaoService(
    private val kakaoClient: KakaoClient,
    @Value("\${kakao.kakaoClientId}") private var kakaoClientId: String,
    @Value("\${kakao.kakaoRedirectUri}") private var kakaoRedirectUri: String,
    @Value("\${kakao.kakaoGrantType}") private var kakaoGrantType: String,
): AuthProviderService {

    fun getAuthorizationCode(): String {
        return kakaoClient.getAuthorizationCode(
            clientId = kakaoClientId, redirectUri = kakaoRedirectUri, responseType = "code"
        )
    }

    override fun fetchAuthToken(authLoginRequest: AuthLogin): OAuthIdAndRefreshTokenDto {
        val kakaoTokenResult = generateAuthToken(authLoginRequest.authorizationCode)
        val kakaoId = getKakaoId(kakaoTokenResult.accessToken)

        return OAuthIdAndRefreshTokenDto(
            oAuthId = kakaoId, refreshToken = kakaoTokenResult.refreshToken ?: "error"
        )
    }

    override fun isSupport(socialType: SocialType): Boolean {
        return socialType == SocialType.KAKAO
    }

    fun generateAuthToken(authorizationCode: String): KakaoTokenResult {
        val kakaoTokenRequest = KakaoTokenRequest(
            grantType = kakaoGrantType,
            clientId = kakaoClientId,
            redirectUri = kakaoRedirectUri,
            code = authorizationCode,
        )
        return kakaoClient.getToken(kakaoTokenRequest)
    }

    fun getKakaoId(accessToken: String): String {
        val kakaoUserInfo = kakaoClient.getUserInfo(accessToken)
        return kakaoUserInfo.id.toString()
    }

}
package lyfe.lyfeBe.auth.service.kakao

import io.github.oshai.kotlinlogging.KotlinLogging
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
    private val KakaoApiClient: KakaoApiClient,
    @Value("\${kakao.kakaoClientId}") private var kakaoClientId: String,
    @Value("\${kakao.kakaoRedirectUri}") private var kakaoRedirectUri: String,
    @Value("\${kakao.kakaoGrantType}") private var kakaoGrantType: String,
    @Value("\${kakao.adminKey}") private var adminKey: String
): AuthProviderService {

    private val log = KotlinLogging.logger {}

    fun getAuthorizationCode(): String {
        return kakaoClient.getAuthorizationCode(
            clientId = kakaoClientId, redirectUri = kakaoRedirectUri, responseType = "code"
        )
    }

    override fun fetchAuthToken(authLoginRequest: AuthLogin): OAuthIdAndRefreshTokenDto {
        log.info { "authLoginRequest: $authLoginRequest" }
        log.info { "authLoginRequest: ${authLoginRequest.idToken}" }
        val kakaoId = getKakaoId(authLoginRequest.idToken
            ?: throw IllegalArgumentException("kakaoId 가 입력되지 않았습니다."))

        return OAuthIdAndRefreshTokenDto(
            oAuthId = kakaoId, refreshToken = "not supported"
        )
    }

    override fun isSupport(socialType: SocialType): Boolean {
        return socialType == SocialType.KAKAO
    }

    override fun revoke(socialId: String, socialRefreshToken: String?): Boolean {
        KakaoApiClient.unlink(
            adminKey = "KakaoAK $adminKey",
            targetIdType = "user_id",
            targetId = socialId
        )
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
        val kakaoUserInfo = KakaoApiClient.getUserInfo("Bearer $accessToken")
        require(kakaoUserInfo.id > 0){ "Kakao 로그인에 실패하였습니다. 사용자 정보를 가져오는 데 문제가 발생하였습니다." }
        return kakaoUserInfo.id.toString()
    }

}
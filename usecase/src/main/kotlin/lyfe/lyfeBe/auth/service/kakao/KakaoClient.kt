package lyfe.lyfeBe.auth.service.kakao

import lyfe.lyfeBe.aop.FeignClientConfiguration
import lyfe.lyfeBe.auth.dto.kakao.KakaoTokenResult
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "kakao", url = "https://kauth.kakao.com", configuration = [KakaoHeaderConfiguration::class, FeignClientConfiguration::class])
interface KakaoClient {

    /**
     * 인가 코드 가져오기
     * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code
     */
    @GetMapping("/oauth/authorize")
    fun getAuthorizationCode(
        @RequestParam("client_id") clientId: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("response_type") responseType: String,
    ): String

    /**
     * 토큰 받기
     * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
     */
    @PostMapping("/oauth/token", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getToken(
        @RequestParam("grant_type") grantType: String,
        @RequestParam("client_id") clientId: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("code") code: String,
    ): KakaoTokenResult
}
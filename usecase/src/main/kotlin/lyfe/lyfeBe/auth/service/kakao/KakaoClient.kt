package lyfe.lyfeBe.auth.service.kakao

import lyfe.lyfeBe.auth.dto.kakao.KakaoTokenRequest
import lyfe.lyfeBe.auth.dto.kakao.KakaoTokenResult
import lyfe.lyfeBe.auth.dto.kakao.KakaoUserInfoResult
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "kakao", url = "https://kapi.kakao.com")
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
    @PostMapping("/oauth/token", consumes = ["application/x-www-form-urlencoded;charset=utf-8"])
    fun getToken(
        @RequestBody kakaoTokenRequest: KakaoTokenRequest
    ): KakaoTokenResult

    /**
     * 사용자 정보 가져오기
     * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
     */
    @GetMapping("/v2/user/me", consumes = ["application/x-www-form-urlencoded;charset=utf-8"])
    fun getUserInfo(
        @RequestHeader("Authorization") authorization: String
    ): KakaoUserInfoResult
}
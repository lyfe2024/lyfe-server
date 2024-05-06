package lyfe.lyfeBe.auth.service.kakao

import lyfe.lyfeBe.aop.FeignClientConfiguration
import lyfe.lyfeBe.auth.dto.kakao.KakaoUserInfoResult
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "kakaoApi", url = "https://kapi.kakao.com", configuration = [KakaoApiHeaderConfiguration::class, FeignClientConfiguration::class])
interface KakaoApiClient {

    /**
     * 사용자 정보 가져오기
     * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
     */
    @GetMapping("/v2/user/me", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserInfo(
        @RequestHeader("Authorization") authorization: String
    ): KakaoUserInfoResult
}
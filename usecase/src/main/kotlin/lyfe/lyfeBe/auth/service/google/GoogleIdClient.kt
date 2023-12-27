package lyfe.lyfeBe.auth.service.google

import lyfe.lyfeBe.auth.dto.google.GoogleUserInfoResult
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "googleIdClient", url = "https://www.googleapis.com")
interface GoogleIdClient {

    /**
     * 사용자 정보 가져오기
     * https://developers.google.com/identity/protocols/oauth2/openid-connect#an-id-tokens-payload
     */
    @GetMapping("/oauth2/v3/userinfo")
    fun getGoogleId(
        @RequestHeader("Authorization") accessToken: String
    ): GoogleUserInfoResult
}

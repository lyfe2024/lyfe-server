package lyfe.lyfeBe.auth.service.google

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "googleIdClient", url = "https://www.googleapis.com", configuration = [GoogleHeaderConfiguration::class])
interface GoogleIdClient {

    /**
     * 사용자 정보 가져오기
     * https://developers.google.com/identity/protocols/oauth2/openid-connect#an-id-tokens-payload
     */
    @GetMapping("/oauth2/v3/userinfo", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getGoogleId(
        @RequestHeader("Authorization") accessToken: String
    ): HashMap<String, String>
}

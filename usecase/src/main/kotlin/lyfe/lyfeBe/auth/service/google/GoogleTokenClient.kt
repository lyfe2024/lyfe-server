package lyfe.lyfeBe.auth.service.google

import lyfe.lyfeBe.auth.dto.google.GoogleTokenResult
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(name = "google", url = "https://oauth2.googleapis.com", configuration = [GoogleHeaderConfiguration::class])
interface GoogleTokenClient {

    /**
     * Google get Token
     */
    @PostMapping("/token", produces = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun requestToken(
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("code") code: String,
        @RequestParam("grant_type") grantType: String,
        @RequestParam("redirect_uri") redirectUri: String,
    ): GoogleTokenResult


}
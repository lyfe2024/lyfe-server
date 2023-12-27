package lyfe.lyfeBe.auth.service.google

import lyfe.lyfeBe.auth.dto.google.GoogleTokenResult
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


@FeignClient(name = "google", url = "https://oauth2.googleapis.com")
interface GoogleTokenClient {

    /**
     * Google get Token
     */
    @PostMapping("/token", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun requestToken(
        @RequestBody params: Map<String, Any>
    ): GoogleTokenResult
}
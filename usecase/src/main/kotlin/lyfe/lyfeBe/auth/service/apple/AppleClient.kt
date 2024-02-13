package lyfe.lyfeBe.auth.service.apple

import lyfe.lyfeBe.auth.dto.apple.ApplePublicKeysResult
import lyfe.lyfeBe.auth.dto.apple.AppleTokenRequest
import lyfe.lyfeBe.auth.dto.apple.AppleTokenResult
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "apple", url = "https://appleid.apple.com")
interface AppleClient {

    /**
     * Apple 공개키 가져오기
     * https://appleid.apple.com/auth/keys
     */
    @GetMapping("/auth/keys")
    fun getApplePublicKeys(): ApplePublicKeysResult

    /**
     * Apple get Token
     * https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens
     */
    @PostMapping("token", consumes = ["application/x-www-form-urlencoded"])
    fun getToken(@RequestBody appleTokenRequest: AppleTokenRequest): AppleTokenResult
}
package lyfe.lyfeBe.auth.service.google

import lyfe.lyfeBe.auth.dto.OAuthIdAndRefreshTokenDto
import lyfe.lyfeBe.auth.dto.google.GoogleLoginRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Service
class GoogleService(
    private val googleTokenClient: GoogleTokenClient,
    private val googleIdClient: GoogleIdClient,

    @Value("\${google.clientId}")
    private var clientId: String,

    @Value("\${google.clientSecret}")
    private var clientSecret: String,

    @Value("\${google.redirectUri}")
    private var redirectUri: String,
) {

    fun fetchGoogleToken(
        googleLoginRequest: GoogleLoginRequest
    ): OAuthIdAndRefreshTokenDto {
        val params = generateTokenParams(googleLoginRequest.authorizationCode)
        val googleTokenDto = googleTokenClient.requestToken(params)
        val googleId = getGoogleId(googleTokenDto.accessToken)
        val refreshToken = googleTokenDto.refreshToken

        return OAuthIdAndRefreshTokenDto(
            oAuthId = googleId,
            refreshToken = refreshToken ?: "error"
        )
    }

    private fun generateTokenParams(authorizationCode: String): Map<String, Any> {
        val code = URLDecoder.decode(authorizationCode, StandardCharsets.UTF_8)
        return mapOf(
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "code" to code,
            "grant_type" to "authorization_code",
            "redirect_uri" to redirectUri
        )
    }

    private fun getGoogleId(accessToken: String): String {
        googleIdClient.getGoogleId("Bearer $accessToken").let {
            return it.sub
        }
    }
}
package lyfe.lyfeBe.auth.service.apple

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import lyfe.lyfeBe.auth.dto.OAuthIdAndRefreshTokenDto
import lyfe.lyfeBe.auth.dto.apple.AppleLoginRequest
import lyfe.lyfeBe.auth.dto.apple.AppleTokenRequest
import lyfe.lyfeBe.auth.dto.apple.AppleTokenResult
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AppleService(
    private val appleClient: AppleClient,
    private val applePublicKeyGenerator: ApplePublicKeyGenerator,
    private val appleJwtParser: AppleJwtParser,
    private val appleCreateClientSecret: AppleCreateClientSecret,

    @Value("\${apple.appleBundleId}")
    private var appleBundleId: String,

    @Value("\${apple.appleRedirectUri}")
    private var appleRedirectUri: String,
) {

    fun fetchAppleToken(
        appleLoginRequest: AppleLoginRequest,
    ): OAuthIdAndRefreshTokenDto {
        val appleId = getAppleId(appleLoginRequest.identityToken)
        val appleTokenResult = generateAuthToken(appleLoginRequest.authorizationCode)

        return OAuthIdAndRefreshTokenDto(
            oAuthId = appleId,
            refreshToken = appleTokenResult.refreshToken ?: "error"
        )
    }

    fun getAppleId(identityToken: String): String {
        val headers = appleJwtParser.parseHeaders(identityToken)
        val applePublicKeys = appleClient.getApplePublicKeys()
        val publicKey = applePublicKeyGenerator.generatePublicKey(headers, applePublicKeys)
        val claims: Claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(identityToken).body
        return claims.subject
    }

    fun generateAuthToken(authorizationCode: String): AppleTokenResult {
        val clientSecret = appleCreateClientSecret.createClientSecret()
        val appleTokenRequest = AppleTokenRequest(
            clientId = appleBundleId,
            clientSecret = clientSecret,
            code = authorizationCode,
            grantType = "authorization_code",
            redirectUri = appleRedirectUri,
        )
        return appleClient.getToken(appleTokenRequest)
    }
}
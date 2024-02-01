package lyfe.lyfeBe.auth.service.apple

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import lyfe.lyfeBe.auth.AuthLogin
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.auth.dto.OAuthIdAndRefreshTokenDto
import lyfe.lyfeBe.auth.dto.apple.AppleTokenRequest
import lyfe.lyfeBe.auth.dto.apple.AppleTokenResult
import lyfe.lyfeBe.auth.service.AuthProviderService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AppleService(
    private val appleClient: AppleClient,
    private val applePublicKeyGenerator: ApplePublicKeyGenerator,
    private val appleJwtParser: AppleJwtParser,
    private val appleCreateClientSecret: AppleCreateClientSecret,
    @Value("\${apple.appleBundleId}") private var appleBundleId: String,
    @Value("\${apple.appleRedirectUri}") private var appleRedirectUri: String,
): AuthProviderService {

    override fun fetchAuthToken(authLoginRequest: AuthLogin): OAuthIdAndRefreshTokenDto {
        val appleId = getAppleId(authLoginRequest.idToken.toString())
        val appleTokenResult = generateAuthToken(authLoginRequest.authorizationCode?: "")

        return OAuthIdAndRefreshTokenDto(
            oAuthId = appleId, refreshToken = appleTokenResult.refreshToken ?: "error"
        )
    }

    override fun isSupport(socialType: SocialType): Boolean {
        return socialType == SocialType.APPLE
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
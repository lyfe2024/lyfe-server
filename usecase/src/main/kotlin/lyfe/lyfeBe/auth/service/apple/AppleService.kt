package lyfe.lyfeBe.auth.service.apple

import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import lyfe.lyfeBe.auth.AuthLogin
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.auth.dto.OAuthIdAndRefreshTokenDto
import lyfe.lyfeBe.auth.dto.apple.AppleRevokeRequest
import lyfe.lyfeBe.auth.dto.apple.AppleTokenResult
import lyfe.lyfeBe.auth.service.AuthProviderService
import lyfe.lyfeBe.error.UnauthenticatedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AppleService(
    private val appleClient: AppleClient,
    private val applePublicKeyGenerator: ApplePublicKeyGenerator,
    private val appleJwtParser: AppleJwtParser,
    private val appleCreateClientSecret: AppleCreateClientSecret,
    @Value("\${apple.appleAud}") private val appleAud: String,
    @Value("\${apple.appleRedirectUri}") private val appleRedirectUri: String,
): AuthProviderService {

    private val log = KotlinLogging.logger {}

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
        return appleClient.getToken(
            clientId = appleAud,
            clientSecret = clientSecret,
            code = authorizationCode,
            grantType = "authorization_code",
            redirectUri = appleRedirectUri
        )
    }

    override fun revoke(socialId: String, socialRefreshToken: String?): Boolean {
        val response = appleClient.revoke(
            AppleRevokeRequest(
                clientId = appleAud,
                clientSecret = appleCreateClientSecret.createClientSecret(),
                token = socialRefreshToken ?: throw UnauthenticatedException("refreshToken is null"),
                tokenTypeHint = "refresh_token"
            )
        )

        require(response.status() == 200)
        return true
    }
}
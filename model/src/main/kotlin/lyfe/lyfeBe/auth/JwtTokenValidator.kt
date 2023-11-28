package lyfe.lyfeBe.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import lyfe.lyfeBe.error.UnauthenticatedException

object JwtTokenValidator {
    private val algorithm = SignatureAlgorithm.HS512
    private val key = Keys.secretKeyFor(algorithm)

    const val AUTHORIZATION_HEADER = "Authorization"
    const val BEARER_TYPE = "Bearer"

    fun verifyToken(token: String): Claims =
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            throw UnauthenticatedException("accessToken의 정보가 올바르지 않습니다.", e)
        }
}
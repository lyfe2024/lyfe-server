package lyfe.lyfeBe.auth.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import lyfe.lyfeBe.auth.TokenStatus
import lyfe.lyfeBe.auth.port.out.RefreshTokenPort
import lyfe.lyfeBe.error.UnauthenticatedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key

@Service
class JwtTokenValidator(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    private val refreshTokenPort: RefreshTokenPort
){
    private val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

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

    fun isPermanentToken(token: String): Boolean {
        val refreshToken = refreshTokenPort.findByRefreshToken(token)
        return if (refreshToken != null) {
            refreshToken.tokenStatus == TokenStatus.PERMANENT
        }else {
            false
        }
    }
}
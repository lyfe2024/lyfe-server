package lyfe.lyfeBe.auth.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import lyfe.lyfeBe.auth.dto.TokenDto
import lyfe.lyfeBe.auth.service.JwtTokenInfo.ACCESS_TOKEN
import lyfe.lyfeBe.auth.service.JwtTokenInfo.EMAIL_CLAIM
import lyfe.lyfeBe.auth.service.JwtTokenInfo.PERMANENT_TOKEN
import lyfe.lyfeBe.auth.service.JwtTokenInfo.PERMANENT_TOKEN_EXPIRE_TIME
import lyfe.lyfeBe.auth.service.JwtTokenInfo.REFRESH_TOKEN
import lyfe.lyfeBe.auth.service.JwtTokenInfo.SIGN_UP_SUBJECT
import lyfe.lyfeBe.auth.service.JwtTokenInfo.TEN_MINUTE
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtTokenProvider(
    @Value("\${jwt.secret}")
    private val secretKey: String,

    @Value("\${jwt.accessTokenExpireTime}")
    private val accessTokenExpireTime: Long,

    @Value("\${jwt.refreshTokenExpireTime}")
    private val refreshTokenExpireTime: Long,
) {
    private val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun generateToken(authentication: Authentication): TokenDto {
        val now = Date().time
        val accessTokenExpiresIn = Date(now + accessTokenExpireTime)

        val accessToken = Jwts.builder()
            .setSubject(ACCESS_TOKEN)
            .claim(EMAIL_CLAIM, authentication.name)
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        val refreshToken = Jwts.builder()
            .setSubject(REFRESH_TOKEN)
            .claim(EMAIL_CLAIM, authentication.name)
            .setExpiration(Date(now + refreshTokenExpireTime))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return TokenDto(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun createTokenForOAuth2(email: String, refreshToken: String): String {
        val now = Date()
        return Jwts.builder()
            .setSubject(SIGN_UP_SUBJECT)
            .setExpiration(Date(now.time + TEN_MINUTE))
            .claim(EMAIL_CLAIM, email)
            .claim(REFRESH_TOKEN, refreshToken)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getRefreshTokenExpireTime(): Long {
        return refreshTokenExpireTime
    }

    fun getPermanentExpireTime(): Long  {
        return PERMANENT_TOKEN_EXPIRE_TIME
    }

    fun createPermanentToken(): String {
        val now = Date()
        return Jwts.builder()
            .setSubject(PERMANENT_TOKEN)
            .setExpiration(Date(now.time + PERMANENT_TOKEN_EXPIRE_TIME))
            .claim(PERMANENT_TOKEN, "")
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }
}
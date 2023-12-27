package lyfe.lyfeBe.auth.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import lyfe.lyfeBe.auth.JwtTokenValidator
import lyfe.lyfeBe.auth.JwtTokenValidator.verifyToken
import lyfe.lyfeBe.auth.dto.TokenDto
import lyfe.lyfeBe.error.UnauthenticatedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.stream.Collectors

@Service
class JwtTokenProvider(
    private val principalDetailService: PrincipalDetailService,

    @Value("\${jwt.secret}")
    private val secretKey: String,

    @Value("\${jwt.accessTokenExpireTime}")
    private val accessTokenExpireTime: Long,

    @Value("\${jwt.refreshTokenExpireTime}")
    private val refreshTokenExpireTime: Long,
) {
    private val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    companion object {
        const val SIGN_UP_SUBJECT: String = "SignUp"
        const val EMAIL_CLAIM: String = "email"
        const val REFRESH_TOKEN: String = "refreshToken"
        const val TEN_MINUTE = 3600000
    }

    fun generateToken(authentication: Authentication): TokenDto {
        val authenticate = authentication.authorities.stream()
            .map { obj -> obj.authority }
            .collect(Collectors.joining(","))

        val now = Date().time
        val accessTokenExpiresIn = Date(now + accessTokenExpireTime)

        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim(JwtTokenValidator.AUTHORITIES_KEY, authenticate)
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        val refreshToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim(JwtTokenValidator.AUTHORITIES_KEY, authenticate)
            .setExpiration(Date(now + refreshTokenExpireTime))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return TokenDto(
            accessToken = accessToken,
            refreshToken = refreshToken
        )

    }

    fun createTokenForOAuth2(email: String): String {
        val now = Date()
        return Jwts.builder()
            .setSubject(SIGN_UP_SUBJECT)
            .setExpiration(Date(now.time + TEN_MINUTE))
            .claim(EMAIL_CLAIM, email)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getAuthentication(refreshToken: String): Authentication?{
        val claims = verifyToken(refreshToken)
        val principal = principalDetailService.loadUserByUsername(claims[EMAIL_CLAIM].toString())
        val authorities: Collection<SimpleGrantedAuthority> =
            claims[JwtTokenValidator.AUTHORITIES_KEY].toString().split(",").stream()
                .map { obj -> SimpleGrantedAuthority(obj) }.collect(Collectors.toList())
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

}
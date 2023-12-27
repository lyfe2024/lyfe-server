package lyfe.lyfeBe.auth.service.apple

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lyfe.lyfeBe.error.UnauthenticatedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class AppleCreateClientSecret(
    @Value("\${apple.appleBundleId}")
    private var appleBundleId: String,

    @Value("\${apple.appleTeamId}")
    private val appleTeamId: String,

    @Value("\${apple.appleSignKeyId}")
    private val appleSignKeyId: Any,

    @Value("\${apple.appleKey}")
    private val appleKey: String

) {
    fun createClientSecret(): String {
        val expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant())
        val jwtHeader: Map<String, Any?> = mapOf("kid" to appleSignKeyId, "alg" to "ES256")

        return Jwts.builder()
            .setHeader(jwtHeader)
            .setIssuer(appleTeamId)
            .setIssuedAt(Date(System.currentTimeMillis())) // 발행 시간
            .setExpiration(expirationDate) // 만료 시간
            .setAudience("https://appleid.apple.com")
            .setSubject(appleBundleId)
            .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
            .compact()
    }

    private fun getPrivateKey(): PrivateKey {
        try {
            val privateKeyBytes = Base64.getDecoder().decode(appleKey.toByteArray())
            val keyFactory = java.security.KeyFactory.getInstance("EC", "BC")
            val privateKeySpec = PKCS8EncodedKeySpec(privateKeyBytes)
            return keyFactory.generatePrivate(privateKeySpec)
        } catch (e: Exception) {
            throw UnauthenticatedException()
        }
    }
}
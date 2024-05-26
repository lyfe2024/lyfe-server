package lyfe.lyfeBe.auth.service.apple

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import lyfe.lyfeBe.error.UnauthenticatedException
import org.springframework.stereotype.Component
import java.util.*

@Component
class AppleJwtParser(private val objectMapper: ObjectMapper) {

    fun parseHeaders(identityToken: String): Map<String, String> {
        try {
            val encodedHeader = identityToken.substring(0, identityToken.indexOf("."))
            val decodedHeader = String(Base64.getUrlDecoder().decode(encodedHeader))
            return objectMapper.readValue(decodedHeader, object : TypeReference<Map<String, String>>() {})
        } catch (e: Exception) {
            throw UnauthenticatedException("Apple OAuth Identity Token 형식이 올바르지 않습니다.")
        }
    }
}

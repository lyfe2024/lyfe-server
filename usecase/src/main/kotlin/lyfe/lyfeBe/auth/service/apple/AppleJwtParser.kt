package lyfe.lyfeBe.auth.service.apple

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import lyfe.lyfeBe.error.UnauthenticatedException
import org.springframework.stereotype.Component
import java.util.Base64

@Component
class AppleJwtParser(private val objectMapper: ObjectMapper) {

    companion object {
        private const val IDENTITY_TOKEN_VALUE_DELIMITER = "\\."
        private const val HEADER_INDEX = 0
    }

    fun parseHeaders(identityToken: String): Map<String, String> {
        try {
            val encodedHeader = identityToken.split(IDENTITY_TOKEN_VALUE_DELIMITER)[HEADER_INDEX]
            val decodedHeader = String(Base64.getUrlDecoder().decode(encodedHeader))
            return objectMapper.readValue(decodedHeader, object : TypeReference<Map<String, String>>() {})
        } catch (e: Exception) {
            throw UnauthenticatedException("Apple OAuth Identity Token 형식이 올바르지 않습니다.")
        }
    }
}

package lyfe.lyfeBe.error

import feign.Response
import feign.codec.ErrorDecoder

class CustomErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception {
        return when (response.status()) {
            400 -> IllegalArgumentException("Bad request from client")
            401 -> UnauthenticatedException("Authentication failed")
            404 -> ResourceNotFoundException("Resource not found")
            500 -> RuntimeException("Internal server error")
            else -> RuntimeException("Unknown error occurred")
        }
    }
}
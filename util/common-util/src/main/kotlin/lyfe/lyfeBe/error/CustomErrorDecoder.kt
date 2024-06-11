package lyfe.lyfeBe.error

import feign.Response
import feign.codec.ErrorDecoder

class CustomErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception {
        return when (response.status()) {
            400 -> IllegalArgumentException("Bad request from OAuth")
            401 -> IllegalArgumentException("OAuth Authentication failed")
            404 -> ResourceNotFoundException("Resource not found from OAuth")
            500 -> RuntimeException("Internal server error from OAuth")
            else -> RuntimeException("Unknown error occurred from OAuth")
        }
    }
}
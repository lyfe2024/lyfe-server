package lyfe.lyfeBe.error

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.lang.Nullable
import org.springframework.web.ErrorResponse
import java.net.URI

class UnauthenticatedException(
    message: String? = "Unauthenticated.",
    @Nullable cause: Throwable? = null
) : RuntimeException(message, cause), ErrorResponse {

    override fun getStatusCode(): HttpStatusCode {
        return HttpStatus.UNAUTHORIZED
    }

    override fun getBody(): ProblemDetail {
        return ProblemDetail.forStatusAndDetail(statusCode, message!!)
            .apply { type = URI.create("/errors/unauthenticated") }
    }

}

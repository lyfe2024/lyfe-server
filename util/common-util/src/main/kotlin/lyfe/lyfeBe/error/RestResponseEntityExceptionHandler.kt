package lyfe.lyfeBe.error

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.*
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.net.URI

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    private val log = KotlinLogging.logger { }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val type = URI.create("/errors/method-argument-not-valid")
        val detail = "Invalid request content."
        val errors: List<String> = ex.detailMessageArguments.flatMap {
            if (it is List<*>) {
                it.mapNotNull { item -> item as? String }
            } else {
                listOf()
            }
        }

        val body = createProblemDetail(ex, status, detail, null, null, request)
        body.type = type
        body.setProperty("invalidFields", errors)
        return handleExceptionInternal(ex, body, headers, status, request)
    }

    /**
     * IllegalArgumentException 은 400 Bad Request 로 처리한다.
     */
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val statusCode = HttpStatus.BAD_REQUEST
        val body = ProblemDetail.forStatusAndDetail(statusCode, ex.message ?: statusCode.reasonPhrase)
            .apply { type = URI.create("/errors/bad-request") }

        return handleExceptionInternal(
            ex = ex,
            body = body,
            headers = HttpHeaders(),
            statusCode = statusCode,
            request = request
        )
    }

    /**
     * UnauthenticatedException 은 401 Unauthorized 로 처리한다.
     */
    @ExceptionHandler(UnauthenticatedException::class)
    fun handleUnauthenticatedException(
        ex: UnauthenticatedException,
        request: WebRequest
    ): ResponseEntity<Any>? =
        handleExceptionInternal(
            ex = ex,
            body = ex.body,
            headers = HttpHeaders(),
            statusCode = ex.statusCode,
            request = request
        )

    /**
     * ForbiddenException 은 403 Forbidden 로 처리한다.
     */
    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(
        ex: ForbiddenException,
        request: WebRequest
    ): ResponseEntity<Any>? =
        handleExceptionInternal(
            ex = ex,
            body = ex.body,
            headers = HttpHeaders(),
            statusCode = ex.statusCode,
            request = request
        )

    /**
     * ResourceNotFoundException 은 404 Not Found 로 처리한다.
     */
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        ex: ResourceNotFoundException,
        request: WebRequest
    ): ResponseEntity<Any>? =
        handleExceptionInternal(
            ex = ex,
            body = ex.body,
            headers = HttpHeaders(),
            statusCode = ex.statusCode,
            request = request
        )

    /**
     * IllegalStateException 은 409 Conflict 로 처리한다.
     */
    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(
        ex: IllegalStateException,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val statusCode = HttpStatus.CONFLICT
        val body = ProblemDetail.forStatusAndDetail(statusCode, ex.message ?: statusCode.reasonPhrase)
            .apply { type = URI.create("/errors/conflict") }

        return handleExceptionInternal(
            ex = ex,
            body = body,
            headers = HttpHeaders(),
            statusCode = statusCode,
            request = request
        )
    }

    /**
     * 나머지 모든 처리되지 않은 Exception 은 500 Internal Server Error 로 처리한다.
     */
    @ExceptionHandler
    fun handleUnclassifiedException(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val statusCode = HttpStatus.INTERNAL_SERVER_ERROR
        val body = ProblemDetail.forStatusAndDetail(statusCode, statusCode.reasonPhrase)

        return handleExceptionInternal(
            ex = ex,
            body = body,
            headers = HttpHeaders(),
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR,
            request = request
        )
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        if (statusCode.is5xxServerError) {
            log.error(ex) { ex.message }
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request)
    }

}

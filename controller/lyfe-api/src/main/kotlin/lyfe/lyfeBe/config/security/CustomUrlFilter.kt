package lyfe.lyfeBe.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lyfe.lyfeBe.error.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import java.net.URI
import kotlin.text.Charsets.UTF_8

@Component
class CustomUrlFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    private val antPathMatcher = AntPathMatcher()

    private val validUrlPatterns = listOf(
        "/health",
        "/",
        "/v1/auth/reissue",
        "/v1/auth/admin",
        "/v1/auth/login",
        "/v1/auth/join",
        "/v1/auth/revoke",
        "/v1/users/check-nickname/**",
        "/v1/users/me",
        "/v1/boards/detail/**",
        "/v1/boards/best/**",
        "/v1/boards/latest/**",
        "/v1/boards/popular/**",
        "/v1/boards/me/**",
        "/v1/boards/**",
        "/v1/comments/**",
        "/v1/topics/**",
        "/v1/policy/**",
        "/v1/notifications",
        "/v1/feedbacks",
        "/v1/reports/**",
    )

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (!isValidUrl(request)) {
            handleInvalidUrl(request, response)
            return
        }
        filterChain.doFilter(request, response)
    }

    private fun isValidUrl(request: HttpServletRequest): Boolean {
        val requestUri = request.requestURI
        return validUrlPatterns.any { antPathMatcher.match(it, requestUri) }
    }

    private fun handleInvalidUrl(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = UTF_8.name()
        response.status = HttpStatus.NOT_FOUND.value()

        val body = objectMapper.writeValueAsString(
            ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ResourceNotFoundException("잘못된 URL입니다.").message!!,
            ).apply {
                type = URI.create("/errors/not-found")
                instance = URI.create(request.requestURI)
            }
        )
        response.writer.write(body)
    }
}

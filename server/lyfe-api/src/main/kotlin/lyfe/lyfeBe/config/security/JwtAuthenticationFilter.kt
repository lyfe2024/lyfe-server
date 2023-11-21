package lyfe.lyfeBe.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lyfe.lyfeBe.auth.application.port.`in`.AuthenticationUseCase
import lyfe.lyfeBe.auth.domain.JwtTokenValidator
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val authenticationUseCase: AuthenticationUseCase
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractToken(request)
        if (token != null && StringUtils.hasText(token)) {
            authenticateUserByToken(token)
            filterChain.doFilter(request, response)
        }
    }

    private fun authenticateUserByToken(token: String) {
        try {
            val authentication = authenticationUseCase.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: RuntimeException) {
            logger.error("Authentication failed: ${e.message}")
            SecurityContextHolder.clearContext()
        }
    }

    fun extractToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(JwtTokenValidator.AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenValidator.BEARER_TYPE)) {
            bearerToken.substring(JwtTokenValidator.BEARER_TYPE.length).trim()
        } else null
    }
}

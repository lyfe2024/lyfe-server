package lyfe.lyfeBe.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lyfe.lyfeBe.auth.port.`in`.AuthenticationUseCase
import lyfe.lyfeBe.auth.service.JwtTokenInfo
import lyfe.lyfeBe.error.UnauthenticatedException
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
        }
        filterChain.doFilter(request, response)
    }

    private fun authenticateUserByToken(token: String) {
        try {
            val authentication = authenticationUseCase.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: UnauthenticatedException) {
            SecurityContextHolder.clearContext()
        }
    }

    fun extractToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(JwtTokenInfo.AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenInfo.BEARER_TYPE)) {
            bearerToken.substring(JwtTokenInfo.BEARER_TYPE.length).trim()
        } else null
    }
}

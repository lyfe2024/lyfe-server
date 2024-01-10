package lyfe.lyfeBe.auth.service

import io.jsonwebtoken.Claims
import lyfe.lyfeBe.auth.PrincipalDetails
import lyfe.lyfeBe.auth.port.`in`.AuthenticationUseCase
import lyfe.lyfeBe.auth.service.JwtTokenInfo.EMAIL_CLAIM
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper
import org.springframework.stereotype.Component

@Component
class AuthenticationService(
    private val principalDetailService: PrincipalDetailService,
    private val jwtTokenValidator: JwtTokenValidator
) : AuthenticationUseCase {

    override fun getAuthentication(token: String): Authentication {
        val claims: Claims = jwtTokenValidator.verifyToken(token)
        val email: String = claims[EMAIL_CLAIM] as String
        val principalDetails: PrincipalDetails =
            principalDetailService.loadUserByUsername(email) as PrincipalDetails

        return UsernamePasswordAuthenticationToken(
            principalDetails,
            principalDetails.password,
            NullAuthoritiesMapper().mapAuthorities(principalDetails.authorities)
        )
    }

}

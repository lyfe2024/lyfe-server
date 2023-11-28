package lyfe.lyfeBe.auth.service

import io.jsonwebtoken.*
import lyfe.lyfeBe.auth.port.`in`.AuthenticationUseCase
import lyfe.lyfeBe.auth.JwtTokenValidator
import lyfe.lyfeBe.auth.PrincipalDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class AuthenticationService(
    private val principalDetailService: PrincipalDetailService
) : AuthenticationUseCase {

    override fun getAuthentication(token: String): Authentication {
        val claims: Claims = JwtTokenValidator.verifyToken(token)
        val principalDetails: PrincipalDetails =
            principalDetailService.loadUserByUsername(claims["userId"] as String) as PrincipalDetails
        return UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.authorities)
    }

}

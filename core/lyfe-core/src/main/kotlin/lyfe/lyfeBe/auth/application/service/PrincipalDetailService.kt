package lyfe.lyfeBe.auth.application.service

import lyfe.lyfeBe.auth.domain.PrincipalDetails
import lyfe.lyfeBe.user.application.port.out.GetUserPort
import lyfe.lyfeBe.user.domain.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class PrincipalDetailService(
    private val getUserPort: GetUserPort
) : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        val principal: User =
            getUserPort.getByIdAndValidateActive(userId.toLong())
        return PrincipalDetails(principal)
    }
}
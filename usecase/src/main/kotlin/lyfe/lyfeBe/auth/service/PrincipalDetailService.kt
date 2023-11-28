package lyfe.lyfeBe.auth.service

import lyfe.lyfeBe.auth.PrincipalDetails
import lyfe.lyfeBe.user.port.out.GetUserPort
import lyfe.lyfeBe.user.User
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
package lyfe.lyfeBe.auth.service

import lyfe.lyfeBe.auth.PrincipalDetails
import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class PrincipalDetailService(
    private val userRepository: UserPort
) : UserDetailsService {
    override fun loadUserByUsername(userEmail: String): UserDetails {
        val principal: User = userRepository.getByEmail(userEmail) ?: throw ResourceNotFoundException("user not found")
        return PrincipalDetails(principal)
    }
}
package lyfe.lyfeBe.auth.service

import lyfe.lyfeBe.auth.PrincipalDetails
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.port.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class PrincipalDetailService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        val principal: User =
            userRepository.getByIdAndValidateActive(userId.toLong())
        return PrincipalDetails(principal)
    }
}
package lyfe.lyfeBe.auth.dto

import lyfe.lyfeBe.user.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class LoginDto(
    val email: String,
    val password: String,
) {
    companion object {
        fun fromUser(user: User): LoginDto {
            return LoginDto(
                email = user.email,
                password = user.email + "lyfe",
            )
        }
    }

    fun toAuthentication(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, password)
    }
}

package lyfe.lyfeBe.auth.service

import lyfe.lyfeBe.auth.PrincipalDetails
import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtils
{
    fun getLoginUserId(userPort : UserPort) : Long{
        val principal = SecurityContextHolder.getContext().authentication.principal as PrincipalDetails
        return userPort.getByEmail(principal.username)?.id
            ?: throw ResourceNotFoundException("해당 계정이 존재하지 않습니다.")
    }

    fun getLoginUser(userPort : UserPort) : User{
        val principal = SecurityContextHolder.getContext().authentication.principal as PrincipalDetails
        return userPort.getByEmail(principal.username)
            ?: throw ResourceNotFoundException("해당 계정이 존재하지 않습니다.")
    }
}
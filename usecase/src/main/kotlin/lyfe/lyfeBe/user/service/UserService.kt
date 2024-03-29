package lyfe.lyfeBe.user.service

import lyfe.lyfeBe.auth.service.SecurityUtils.getLoginUserId
import lyfe.lyfeBe.user.UserUpdate
import lyfe.lyfeBe.user.dto.UpdateUserDto
import lyfe.lyfeBe.user.dto.UserDto
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userPort: UserPort,
) {
    companion object {
        private val NICKNAME_PATTERN = Regex("^[a-zA-Z0-9ㄱ-ㅎ가-힣]{2,10}$")
    }

    fun getMe(): UserDto {
        val loginUserId = getLoginUserId(userPort)
        val user = userPort.getById(loginUserId)
        return UserDto.from(user)
    }

    @Transactional
    fun update(userUpdate: UserUpdate): UpdateUserDto {
        val loginUserId = getLoginUserId(userPort)
        val user = userPort.getById(loginUserId)
        val updatedUser = user.let {
            var tempUser = it
            userUpdate.nickname?.also { nickname -> tempUser = tempUser.updateNickName(nickname) }
            userUpdate.profileUrl?.also { profileUrl -> tempUser = tempUser.updateProfileUrl(profileUrl) }
            tempUser
        }
        return UpdateUserDto(userPort.update(updatedUser).id)
    }

    fun checkNickname(nickname: String) {
        validateNickname(nickname)
        check(!userPort.existsByNickname(nickname = nickname)) { "이미 사용중인 닉네임입니다." }
    }

    fun validateNickname(nickname: String) {
        require(NICKNAME_PATTERN.matches(nickname)) { "닉네임 규칙에 적합하지 않습니다." }
    }
}
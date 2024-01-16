package lyfe.lyfeBe.user.service

import lyfe.lyfeBe.auth.service.SecurityUtils
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.image.port.out.ImagePort
import lyfe.lyfeBe.user.UserUpdate
import lyfe.lyfeBe.user.dto.UpdateUserDto
import lyfe.lyfeBe.user.dto.UserDto
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userPort: UserPort,
    private val boardPort: BoardPort,
    private val imagePort: ImagePort
) {
    companion object {
        private val NICKNAME_PATTERN = Regex("^[a-zA-Z0-9ㄱ-ㅎ가-힣]{2,10}$")
    }

    fun getMe(): UserDto {
        val loginUserId = getLoginUserId()
        val user = userPort.getById(loginUserId)
        val image = imagePort.getByUserId(user.id)
        return UserDto.from(user, image?.url ?: "")
    }

    @Transactional
    fun update(userUpdate: UserUpdate): UpdateUserDto {
        val loginUserId = getLoginUserId()
        val user = userPort.getById(loginUserId).updateUserInfo(userUpdate)
        val image = imagePort.getByUserId(user.id)?.update(userUpdate)
        image?.let { imagePort.update(it) }
        return UpdateUserDto(userPort.update(user).id)
    }

    fun checkNickname(nickname: String) {
        validateNickname(nickname)
        check(!userPort.existsByNickname(nickname = nickname)) { "이미 사용중인 닉네임입니다." }
    }

    /**
     * 내가 쓴 글 조회
     */
    fun getMyBoardList(boardType: BoardType, pageable: Pageable) {
        val loginUserId = getLoginUserId()
        val user = userPort.getById(loginUserId)
        boardPort.getByUserAndBoardType(user.id, boardType, pageable)
    }

    fun validateNickname(nickname: String) {
        require(NICKNAME_PATTERN.matches(nickname)) { "닉네임 규칙에 적합하지 않습니다." }
    }

    fun getLoginUserId(): Long {
        return SecurityUtils.getLoginUserId(userPort)
    }
}
package lyfe.lyfeBe.user.service

import io.github.oshai.kotlinlogging.KotlinLogging
import lyfe.lyfeBe.auth.service.SecurityUtils
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.user.UserUpdate
import lyfe.lyfeBe.user.dto.UpdateUserDto
import lyfe.lyfeBe.user.dto.UserDto
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.whisky.out.WhiskyPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userPort: UserPort,
    private val boardPort: BoardPort,
    private val whiskyPort: WhiskyPort,
    private val commentPort: CommentPort
) {
    companion object {
        private val NICKNAME_PATTERN = Regex("^[a-zA-Z0-9ㄱ-ㅎ가-힣]{2,10}$")
    }

    private val log = KotlinLogging.logger { }

    fun getMe(): UserDto {
        val loginUserId = getLoginUserId()
        val user = userPort.getById(loginUserId)
        val image = user.profileUrl
        return UserDto.from(user)
    }

    @Transactional
    fun update(userUpdate: UserUpdate): UpdateUserDto {
        val loginUserId = getLoginUserId()
        val user = userPort.getById(loginUserId).updateNickName(userUpdate.nickname)
        return UpdateUserDto(userPort.update(user).id)
    }

    fun checkNickname(nickname: String) {
        validateNickname(nickname)
        check(!userPort.existsByNickname(nickname = nickname)) { "이미 사용중인 닉네임입니다." }
    }



    fun validateNickname(nickname: String) {
        require(NICKNAME_PATTERN.matches(nickname)) { "닉네임 규칙에 적합하지 않습니다." }
    }

    fun getLoginUserId(): Long {
        return SecurityUtils.getLoginUserId(userPort)
    }

    private fun fetchCommentCount(boardId: Long) = commentPort.countByBoardId(boardId)

    private fun fetchWhiskyCount(boardId: Long) = whiskyPort.countByBoardId(boardId)
}
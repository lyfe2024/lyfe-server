package lyfe.lyfeBe.whisky

import jakarta.transaction.Transactional
import lyfe.lyfeBe.auth.service.SecurityUtils.getLoginUser
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.whisky.dto.SaveWhiskyDto
import lyfe.lyfeBe.whisky.out.WhiskyPort
import org.springframework.stereotype.Service

@Service
class WhiskyService(
    private val whiskyPort: WhiskyPort,
    private val userPort: UserPort,
    private val boardPort: BoardPort
) {
    @Transactional
    fun createWhiskeyBoard(whiskyCreate: WhiskyCreate): Any {
        val user = getLoginUser(userPort)
        val board = boardPort.getById(whiskyCreate.id)

        return if(whiskyPort.existByBoardIdAndUserId(whiskyCreate.id, user.id)){
            whiskyPort.deleteByBoardIdAndUserId(whiskyCreate.id, user.id)
            false
        } else {
            val whisky = Whisky.from(board, user)
            SaveWhiskyDto.from(whiskyPort.create(whisky).id)
        }
    }
}
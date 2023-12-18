package lyfe.lyfeBe.whisky

import jakarta.transaction.Transactional
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
    fun create(whiskyCreate: WhiskyCreate): SaveWhiskyDto {

        whiskyPort.assertNoExistingWhisky(whiskyCreate.boardId, whiskyCreate.userId)

        val board = boardPort.getById(whiskyCreate.boardId)
        val user = userPort.getById(whiskyCreate.userId)
        val id = whiskyPort.create(
            Whisky.from(board, user)
        ).id

        println("id = ${id}")
        return SaveWhiskyDto.from(id)
    }

    @Transactional
    fun delete(whiskyDelete: WhiskyDelete) {
            whiskyPort.delete(whiskyDelete.boardId, whiskyDelete.userId)
    }


}
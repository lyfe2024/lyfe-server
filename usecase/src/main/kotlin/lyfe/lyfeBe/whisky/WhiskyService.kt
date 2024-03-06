package lyfe.lyfeBe.whisky

import jakarta.transaction.Transactional
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.whisky.out.WhiskyPort
import org.springframework.stereotype.Service

@Service
class WhiskyService(
    private val whiskyPort: WhiskyPort,
    private val userPort: UserPort,
    private val boardPort: BoardPort
) {


    @Transactional
    fun switchWhiskyLikeState(whiskyCreate: WhiskyCreate) {
        val existingWhisky = whiskyPort.assertNoExistingWhisky(whiskyCreate.boardId, whiskyCreate.userId)

        if (existingWhisky != null) {
            // 좋아요가 이미 존재한다면 삭제하고 함수 종료
            whiskyPort.delete(whiskyCreate.boardId, whiskyCreate.userId)
        } else {
            // 좋아요가 존재하지 않는다면 추가
            val board = boardPort.getById(whiskyCreate.boardId)
            val user = userPort.getById(whiskyCreate.userId)
            whiskyPort.create(
                Whisky.from(board, user)
            )
        }
    }

    @Transactional
    fun delete(whiskyDelete: WhiskyDelete) {
        whiskyPort.delete(whiskyDelete.boardId, whiskyDelete.userId)
    }
}
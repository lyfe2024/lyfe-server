package initTest.lyfe.lyfeBe.test.whisky

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.web.whisky.req.WhiskySaveRequest
import lyfe.lyfeBe.whisky.Whisky
import lyfe.lyfeBe.whisky.WhiskyCreate
import java.time.Instant

class WhiskyFactory {
    companion object {

        fun createTestWhisky(
            id: Long = 1L,
            user: User,
            board: Board,
            createdAt: Instant = Instant.now()
        ): Whisky {
            return Whisky(
                id = id,
                user = user,
                board = board,
                createdAt = createdAt
            )
        }


        fun createWhiskyCreate() = WhiskyCreate(
            boardId = 1L,
            userId = 1L
        )
        fun createWhiskySaveRequest() = WhiskySaveRequest(
            userId = 1L,
            boardId = 1L
        )

    }
}

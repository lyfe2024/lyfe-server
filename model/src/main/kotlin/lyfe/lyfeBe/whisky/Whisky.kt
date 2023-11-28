package lyfe.lyfeBe.whisky

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.user.User
import java.time.Instant

data class Whisky(
    val id: Long,
    val user : User,
    val board: Board,
    val createdAt: Instant?,
)

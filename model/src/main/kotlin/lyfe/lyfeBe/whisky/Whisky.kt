package lyfe.lyfeBe.whisky

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.user.User
import java.time.Instant

data class Whisky(
    val id: Long,
    val user: User,
    val board: Board,
    val createdAt: Instant?,
) {


    companion object {

        fun from(board: Board, user: User) =
            Whisky(
                id = 0,
                user = User.from(user),
                board = Board.from(board),
                createdAt = Instant.now(),
            )
    }
}

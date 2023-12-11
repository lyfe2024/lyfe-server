package lyfe.lyfeBe.comment

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.user.User
import java.time.Instant

data class Comment(
    val id: Long,
    val content: String,
    val commentGroupId: Long?,
    val user: User,
    val board: Board,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val visibility: Boolean,
)

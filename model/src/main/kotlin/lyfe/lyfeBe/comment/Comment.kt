package lyfe.lyfeBe.comment

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.user.User
import java.time.Instant

data class Comment(
    val id: Long,
    var content: String,
    val commentGroupId: Long?,
    val user: User,
    val board: Board,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val visibility: Boolean,
){
    companion object {
        fun create(
            content: String,
            commentGroupId: Long?,
            user: User,
            board: Board,
        ): Comment {
            return Comment(
                id = 0,
                content = content,
                commentGroupId = commentGroupId,
                user = user,
                board = board,
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
                deletedAt = null,
                visibility = true,
            )
        }
    }
}

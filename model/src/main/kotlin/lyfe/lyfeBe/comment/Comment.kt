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
    val updatedAt: Instant?
){
    fun update(commentUpdate: CommentUpdate) =
        Comment(
            id = commentUpdate.commentId,
            content = commentUpdate.content,
            commentGroupId = commentGroupId,
            user = user,
            board = board,
            createdAt = createdAt,
            updatedAt = Instant.now()
        )
    companion object {
        fun from(
            commentCreate: CommentCreate,
            user: User,
            board: Board,
        ): Comment {
            return Comment(
                id = 0,
                content = commentCreate.content,
                commentGroupId = commentCreate.commentGroupId,
                user = user,
                board = board,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }
    }
}

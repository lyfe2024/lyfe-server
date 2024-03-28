package lyfe.lyfeBe.board

import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import java.time.Instant

data class Board(
    val id: Long ,
    val title: String,
    val content: String,
    val boardType: BoardType,
    val imageUrl : String? = null,
    val user: User,
    val topic: Topic,
    val whiskyCount : Int? = null ,
    val createdAt: Instant?,
    val updatedAt: Instant?
) {
    fun update(boardUpdate: BoardUpdate, userId: Long): Board {
        validateUser(userId)
        return Board(
            id = boardUpdate.boardId,
            title = boardUpdate.title,
            content = boardUpdate.content,
            boardType = boardType,
            imageUrl = boardUpdate.imageUrl,
            user = user,
            topic = topic,
            createdAt = createdAt,
            updatedAt = Instant.now()
        )
    }
    private fun validateUser(userId: Long) {
        if (user.id != userId) {
            throw IllegalArgumentException("User is not authorized to update this board")
        }
    }

    companion object {

        fun from(boardCreate: BoardCreate, user: User, topic: Topic) =
            Board(
                id = 0,
                title = boardCreate.title,
                content = boardCreate.content,
                boardType = boardCreate.boardType,
                user = user,
                topic = topic,
                imageUrl = boardCreate.imageUrl,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )

        fun from(boardCreate: Board): Board {
            return Board(
                id = boardCreate.id,
                title = boardCreate.title,
                content = boardCreate.content,
                boardType = boardCreate.boardType,
                imageUrl = boardCreate.imageUrl,
                user = boardCreate.user,
                topic = boardCreate.topic,
                createdAt = boardCreate.createdAt,
                updatedAt = boardCreate.updatedAt
            )
        }
    }
}

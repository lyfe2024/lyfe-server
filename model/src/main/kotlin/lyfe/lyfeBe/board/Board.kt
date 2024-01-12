package lyfe.lyfeBe.board

import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import java.time.Instant

data class Board(
    val id: Long ,
    val title: String,
    val content: String,
    val boardType: BoardType,
    val user: User,
    val topic: Topic,
    val createdAt: Instant?,
    val updatedAt: Instant?
) {
    fun update(boardUpdate: BoardUpdate) =
         Board(
            id = boardUpdate.boardId,
            title = boardUpdate.title,
            content = boardUpdate.content,
            boardType = boardType,
            user = user,
            topic = topic,
            createdAt = createdAt,
            updatedAt = Instant.now()
        )

    companion object {

        fun from(boardCreate: BoardCreate, user: User, topic: Topic) =
            Board(
                id = 0,
                title = boardCreate.title,
                content = boardCreate.content,
                boardType = boardCreate.boardType,
                user = user,
                topic = topic,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )

        fun from(boardCreate: Board): Board {
            return Board(
                id = boardCreate.id,
                title = boardCreate.title,
                content = boardCreate.content,
                boardType = boardCreate.boardType,
                user = boardCreate.user,
                topic = boardCreate.topic,
                createdAt = boardCreate.createdAt,
                updatedAt = boardCreate.updatedAt
            )
        }
    }
}

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
    val updatedAt: Instant?,
    val visibility: Boolean,
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
            updatedAt = Instant.now(),
            visibility = visibility
        )

    companion object {

        fun from(board: Board, user: User) =
                Board(
                        id = board.id,
                        title = board.title,
                        content = board.content,
                        boardType = board.boardType,
                        user = user,
                        topic = board.topic,
                        createdAt = Instant.now(),
                        updatedAt = Instant.now(),
                        visibility = true
                )

        fun from(boardCreate: BoardCreate, user: User, topic: Topic) =
            Board(
                id = 0,
                title = boardCreate.title,
                content = boardCreate.content,
                boardType = boardCreate.boardType,
                user = user,
                topic = topic,
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
                visibility = true
            )
    }
}

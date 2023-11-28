package lyfe.lyfeBe.board

import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import java.time.Instant

data class Board(
    val id: Long,
    val title: String,
    val content: String?,
    val picture: Image?,
    val boardType: BoardType,
    val user: User,
    val topic: Topic,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val deletedAt: Instant?,
    val visibility: Boolean,
)

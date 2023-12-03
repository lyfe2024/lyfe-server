package lyfe.lyfeBe.board

import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.image.Image

data class BoardCreate(
    val title: String,
    val content: String,
    val boardType: BoardType,
    val userId: Long,
    val topicId: Long,
    val picture: Image? = null
)

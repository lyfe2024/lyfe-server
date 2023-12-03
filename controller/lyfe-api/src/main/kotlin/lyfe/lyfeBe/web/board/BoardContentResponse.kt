package lyfe.lyfeBe.web.board

import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.web.user.UserResponse

data class BoardContentResponse(
    val id: Long,
    val title: String,
    val content: String,
    val boardType: BoardType,
    val createdAt: String,
    val updatedAt: String,
    val user: UserResponse,
    val whiskyCount: Int,
    val commentCount: Int,
    val isLike: Boolean
)

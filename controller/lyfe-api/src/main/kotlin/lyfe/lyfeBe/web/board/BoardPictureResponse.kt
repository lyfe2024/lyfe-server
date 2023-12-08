package lyfe.lyfeBe.web.board

import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.web.user.UserResponse

data class BoardPictureResponse(
    val id: Long,
    val title : String,
    val picture : PictureResponse,
    val date: String,
    val boardType: BoardType,
    val user : UserResponse,
    val whiskyCount : Int,
    val commentCount : Int,
)
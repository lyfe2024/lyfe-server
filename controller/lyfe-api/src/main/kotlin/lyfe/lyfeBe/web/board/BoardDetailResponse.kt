package lyfe.lyfeBe.web.board

import com.fasterxml.jackson.annotation.JsonInclude
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.web.user.UserResponse

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BoardDetailResponse(
    val id: Long,
    val user : UserResponse,
    val title : String?,
    val picture : PictureResponse?,
    val content : String?,
    val boardType: BoardType,
    val whiskyCount : Int,
    val commentCount : Int,
    val isLike : Boolean,
    val updatedAt: String,
)

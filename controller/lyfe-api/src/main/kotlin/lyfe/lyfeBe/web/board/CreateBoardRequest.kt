package lyfe.lyfeBe.web.board

import lyfe.lyfeBe.board.BoardType

data class CreateBoardRequest(
    val title : String?,
    val picture : String?,
    val content : String?,
    val boardType: BoardType,
)

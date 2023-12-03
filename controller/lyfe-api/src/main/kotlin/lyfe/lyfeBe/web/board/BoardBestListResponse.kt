package lyfe.lyfeBe.web.board

data class BoardBestListResponse(
    val topic: String,
    val date: String,
    val boardPictureList: List<BoardPictureResponse>,
    val boardContentList: List<BoardContentResponse>
)

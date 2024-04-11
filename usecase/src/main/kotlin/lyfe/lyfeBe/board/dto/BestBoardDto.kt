package lyfe.lyfeBe.board.dto

import java.time.LocalDate

data class BestBoardDto(
    val date : LocalDate,
    val topic : String,
    val boardPictureList: List<BoardDto>,
    val boardList : List<BoardDto>
)

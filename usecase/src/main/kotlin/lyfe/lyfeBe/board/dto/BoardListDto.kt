package lyfe.lyfeBe.board.dto

data class BoardListDto (
    val list : List<BoardDto>
){
    companion object {
        fun toListDto(list : List<BoardDto>) : BoardListDto {
            return BoardListDto(list)
        }
    }
}
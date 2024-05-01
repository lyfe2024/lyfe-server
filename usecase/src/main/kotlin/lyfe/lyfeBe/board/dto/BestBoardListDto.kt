package lyfe.lyfeBe.board.dto

data class BestBoardListDto(
    val list : List<BestBoardDto>
){
    companion object {
        fun toListDto(list : List<BestBoardDto>) : BestBoardListDto {
            return BestBoardListDto(list)
        }
    }
}


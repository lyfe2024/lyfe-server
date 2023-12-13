package lyfe.lyfeBe.board.dto

import lyfe.lyfeBe.board.Board

data class UpdateBoardDto(
    val id : Long,
){
    companion object {
        fun from(board : Board): UpdateBoardDto {
            return UpdateBoardDto(
                id = board.id,
            )
        }
    }
}

package lyfe.lyfeBe.board.dto

import lyfe.lyfeBe.board.Board

data class SaveBoardDto(
    val id : Long,
){
    companion object {
        fun from(board : Board): SaveBoardDto {
            return SaveBoardDto(
                id = board.id,
            )
        }
    }
}

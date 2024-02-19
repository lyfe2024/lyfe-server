package lyfe.lyfeBe.board.dto

import lyfe.lyfeBe.board.Board

data class BoardDtoAssembly(
    val board: Board,
    val whiskyCount: Int,
    val commentCount: Int
)
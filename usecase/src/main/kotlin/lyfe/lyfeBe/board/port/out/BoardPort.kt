package lyfe.lyfeBe.board.port.out

import lyfe.lyfeBe.board.Board

interface BoardPort {

    fun getById(id: Long): Board
}
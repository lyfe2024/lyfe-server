package lyfe.lyfeBe.board.port.out

import lyfe.lyfeBe.board.Board

interface GetBoardPort {

    fun getById(id: Long): Board
}
package lyfe.lyfeBe.board.port.out

import lyfe.lyfeBe.board.Board

interface BoardPort {
    fun getById(id: Long): Board
    fun create(board: Board): Board
    fun update(board: Board): Board
    fun findAll(paging: Long, size: Int): List<Board>
}
package lyfe.lyfeBe.board.port.out

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardPort {
    fun getById(id: Long): Board
    fun create(board: Board): Board
    fun update(board: Board): Board

    fun findByIdCursorId(cursorId: Long, date: String?, pageable: Pageable, type: BoardType): List<Board>
    fun findPopularBoards(cursor: String, count: Int, date: String?, type: BoardType): List<Board>
}
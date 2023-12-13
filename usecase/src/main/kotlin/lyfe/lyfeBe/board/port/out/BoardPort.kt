package lyfe.lyfeBe.board.port.out

import lyfe.lyfeBe.board.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardPort {
    fun getById(id: Long): Board
    fun create(board: Board): Board
    fun update(board: Board): Board
    fun findByIdCursorId(boardId: Long, paging: Pageable): Page<Board>
}
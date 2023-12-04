package lyfe.lyfeBe.board.out

import lyfe.lyfeBe.board.Board
import org.springframework.data.domain.PageRequest
import java.util.*

interface BoardPort {
    fun findById(id: Long): Board
    fun create(board: Board): Board
    fun update(board: Board): Board
    fun findAll(paging: PageRequest): List<Board>
}
package lyfe.lyfeBe.board.port

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardCreate
import org.springframework.data.domain.PageRequest
import java.util.*

interface BoardRepository {
    fun findById(id: Long): Optional<Board>
    fun create(board: Board): Board
    fun update(board: Board): Board
    fun findAll(paging: PageRequest): List<Board>
}
package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.fomatter.CursorGenerator.Companion.createCursorValue
import org.springframework.data.domain.Pageable
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class FakeBoardRepository : BoardPort {

    private val autoGeneratedId = AtomicLong(0)
    private val table: MutableList<Board> = Collections.synchronizedList(ArrayList())

    override fun getById(id: Long) = table.find { it.id == id }!!

    override fun create(board: Board): Board {
        return if (board.id == 0L) {
            val newBoard = board.copy(id = autoGeneratedId.incrementAndGet())
            table.add(newBoard)
            newBoard
        } else {
            table.removeIf { it.id == board.id }
            table.add(board)
            board
        }
    }

    override fun update(board: Board): Board {
        val index = table.indexOfFirst { it.id == board.id }
        if (index != -1) {
            table[index] = board
        }
        return board
    }
    override fun findByIdCursorId(cursorId: Long, date: String?, page: Pageable, type: BoardType): List<Board> {
        // 필터링: cursorId보다 작고, type이 일치하는 Board 객체들을 선택
        val boards = table.filter {
            it.id < cursorId && it.boardType == type &&
                    (date == null || it.createdAt.toString() == date) // date 처리는 단순화됨
        }.sortedByDescending { it.id } // 내림차순 정렬

        // 페이징 처리
        val pageSize = page.pageSize
        val offset = page.offset.toInt()
        val toIndex = (offset + pageSize).coerceAtMost(boards.size)

        return if (offset < boards.size) boards.subList(offset, toIndex) else emptyList()
    }

    override fun findPopularBoards(cursor: String, count: Int, date: String?, type: BoardType): List<Board> {
        // 단순히 필터링 및 정렬된 게시판 리스트를 반환
        return table.filter { board ->
            board.boardType == type &&
                    (date == null || board.createdAt.toString().startsWith(date))
        }.sortedWith(compareByDescending<Board> { it.whiskyCount }.thenByDescending { it.id })
            .take(count) // 요청된 개수만큼의 결과 반환
    }
    fun clear() {
        table.clear()
    }
}
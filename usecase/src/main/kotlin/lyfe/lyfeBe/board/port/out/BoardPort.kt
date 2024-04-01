package lyfe.lyfeBe.board.port.out

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import org.springframework.data.domain.Pageable
import java.time.LocalDate

interface BoardPort {
    fun getById(id: Long): Board
    fun create(board: Board): Board
    fun update(board: Board): Board
    fun getBoardsWithCursorAndUser(
        cursorId: Long,
        type: BoardType,
        userId: Long,
        pageable: Pageable
    ): List<Board>
    fun findBoardWithCursorAndTopic(
        cursorId: Long,
        type: BoardType,
        topicId: Long?,
        pageable: Pageable
    ): List<Board>

    fun findPopularBoardsWithWhisky(
        cursorId: Long,
        topicId: Long,
        type: BoardType,
        pageable: Pageable
    ): List<Board>

    fun findPopularBoardsWithComment(
        cursorId: Long,
        topicId: Long,
        type: BoardType,
        pageable: Pageable
    ): List<Board>
    fun findUniqueDatesBeforeCursor(cursor: LocalDate, pageable: Pageable): List<LocalDate>
    fun findByDateAndType(date: LocalDate, type: BoardType, pageable: Pageable): List<Board>

}
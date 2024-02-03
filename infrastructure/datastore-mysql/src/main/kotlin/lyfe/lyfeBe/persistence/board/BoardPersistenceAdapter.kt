package lyfe.lyfeBe.persistence.board

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.error.ResourceNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class BoardPersistenceAdapter(
    private val boardJpaRepository: BoardJpaRepository,
) : BoardPort {
    override fun getById(id: Long): Board {
        return boardJpaRepository.findByIdOrNull(id)?.toDomain()
            ?: throw ResourceNotFoundException("해당하는 게시글이 존재하지 않습니다.")
    }

    override fun create(board: Board) =
        boardJpaRepository.save(BoardJpaEntity.from(board)).toDomain()

    override fun update(board: Board): Board {
        val update = BoardJpaEntity.update(board)
        return boardJpaRepository.save(update).toDomain()
    }

    override fun findByIdCursorId(cursorId: Long, date: String?, pageable: Pageable, type: BoardType) =
        boardJpaRepository.findByIdCursorId(cursorId, date, type, pageable).map { it.toDomain() }


    override fun findPopularBoards(cursor: String, count: Int, date: String?, type: BoardType) =
        boardJpaRepository.findBoardsWithWhiskyCount(cursor, count, date, type).map { it.toDomain() }

}
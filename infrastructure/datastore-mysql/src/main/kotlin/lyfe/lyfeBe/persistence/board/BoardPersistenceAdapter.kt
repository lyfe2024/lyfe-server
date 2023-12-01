package lyfe.lyfeBe.persistence.board

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.port.BoardRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class BoardPersistenceAdapter(
    private val boardJpaRepository: BoardJpaRepository,
) : BoardRepository {
    override fun findById(id: Long): Optional<Board> {
        return boardJpaRepository.findById(id).map { it.toDomain() }
    }

    override fun create(board: Board) =
        boardJpaRepository.save(BoardJpaEntity.from(board)).toDomain()

    override fun update(board: Board): Board {
        val update = BoardJpaEntity.update(board)
        return boardJpaRepository.save(update).toDomain()
    }

    override fun findAll(paging: PageRequest) =
        boardJpaRepository.findAll(paging).map { it.toDomain() }.toList()

    //FIXME N+1문제는 어떻게 해결할까?
}
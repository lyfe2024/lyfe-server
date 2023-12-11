package lyfe.lyfeBe.persistence.board

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.port.out.BoardPort
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class BoardPersistenceAdapter(
    private val boardJpaRepository: BoardJpaRepository,
) : BoardPort {
    override fun findById(id: Long): Board {
        return boardJpaRepository.findById(id).orElseThrow { RuntimeException("Board not found") }
                .toDomain()
    }

    override fun create(board: Board) =
        boardJpaRepository.save(BoardJpaEntity.from(board)).toDomain()

    override fun update(board: Board): Board {
        val update = BoardJpaEntity.update(board)
        return boardJpaRepository.save(update).toDomain()
    }

    override fun findAll(id: Long, size: Int): List<Board> {
        val pageable = PageRequest.of(0, size)
      return  boardJpaRepository.findByIdLessThanOrderByIdDesc(id, pageable).map { it.toDomain() }.toList()

    }
    //FIXME N+1문제는 어떻게 해결할까?
}
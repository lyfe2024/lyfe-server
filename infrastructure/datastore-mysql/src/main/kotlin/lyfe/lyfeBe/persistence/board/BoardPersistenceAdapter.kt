package lyfe.lyfeBe.persistence.board

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.error.ResourceNotFoundException
import org.springframework.data.domain.Page
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

    override fun findByIdCursorId(boardId: Long, paging: Pageable) =
        boardJpaRepository.findByIdCursorId(boardId, paging).map { it.toDomain() }
    //FIXME N+1문제는 어떻게 해결할까?
}
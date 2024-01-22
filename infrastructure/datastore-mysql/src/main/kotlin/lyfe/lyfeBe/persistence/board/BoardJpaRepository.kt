package lyfe.lyfeBe.persistence.board

import lyfe.lyfeBe.board.BoardType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardJpaRepository: JpaRepository<BoardJpaEntity, Long> {

    @Query("SELECT t FROM BoardJpaEntity t WHERE t.id < :cursorId")
    fun findByIdCursorId(cursorId: Long, pageable: Pageable): Page<BoardJpaEntity>

    @Query("SELECT t FROM BoardJpaEntity t WHERE t.user.id = :userId AND t.boardType = :boardType AND t.id < :cursorId")
    fun findByUserIdAndBoardTypeAndCursorId(userId: Long, boardType: BoardType, cursorId: Long, pageable: Pageable): Page<BoardJpaEntity>
}
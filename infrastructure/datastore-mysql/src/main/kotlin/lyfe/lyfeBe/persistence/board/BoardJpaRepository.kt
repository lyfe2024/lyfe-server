package lyfe.lyfeBe.persistence.board

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface BoardJpaRepository : JpaRepository<BoardJpaEntity, Long> {

    @Query("SELECT t FROM BoardJpaEntity t WHERE t.id < :cursorId")
    fun findByIdCursorId(cursorId: Long, pageable: Pageable): Page<BoardJpaEntity>


    @Query("SELECT b FROM BoardJpaEntity b " +
            "LEFT JOIN WhiskyJpaEntity w ON b.id = w.board.id " +
            "WHERE b.boardType = 'BOARD' " +
            "AND b.baseEntity.createdAt >= CURRENT_DATE " +
            "AND b.id < :cursorId " +
            "GROUP BY b.id ORDER BY COUNT(w.id) DESC, b.baseEntity.createdAt DESC")

    fun findPopularBoards(cursorId: Long, pageable: Pageable): Page<BoardJpaEntity>
}
package lyfe.lyfeBe.persistence.board

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardJpaRepository : JpaRepository<BoardJpaEntity, Long> {

    @Query("SELECT b FROM BoardJpaEntity b WHERE b.id < :cursorId AND b.boardType = 'BOARD'")
    fun findByIdCursorId(cursorId: Long, pageable: Pageable): Page<BoardJpaEntity>


    @Query(
        "SELECT b FROM BoardJpaEntity b " +
                "LEFT JOIN WhiskyJpaEntity w ON b.id = w.board.id " +
                "WHERE b.boardType = 'BOARD' " +
                "AND b.baseEntity.createdAt <= CURRENT_TIMESTAMP " +
                "AND b.id < :cursorId " +
                "GROUP BY b.id ORDER BY COUNT(w.id) DESC, b.baseEntity.createdAt DESC"
    )

    fun findRecentPopularBoards(cursorId: Long, pageable: Pageable): Page<BoardJpaEntity>


    @Query(
        "SELECT b FROM BoardJpaEntity b " +
                "WHERE b.boardType = 'BOARD_PICTURE' " +
                "AND b.baseEntity.createdAt  <= CURRENT_TIMESTAMP " +
                "AND b.id < :cursorId " +
                "ORDER BY b.baseEntity.createdAt DESC"
    )
    fun findRecentBoardPictures(cursorId: Long, pageable: Pageable): Page<BoardJpaEntity>
}
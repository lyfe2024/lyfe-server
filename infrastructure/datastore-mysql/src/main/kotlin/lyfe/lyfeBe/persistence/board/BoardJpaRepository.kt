package lyfe.lyfeBe.persistence.board

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface BoardJpaRepository : JpaRepository<BoardJpaEntity, Long> {

    @Query("SELECT b FROM BoardJpaEntity b WHERE b.id < :cursorId AND b.boardType = 'BOARD'")
    fun findByIdCursorId(cursorId: Long, pageable: Pageable): Page<BoardJpaEntity>

    @Query("""
    SELECT new lyfe.lyfeBe.persistence.board.BoardWithCursorValue(
        b, COUNT(w),
        CONCAT(
            LPAD(CAST(POW(10, 10) - COUNT(w) AS STRING), 10, '0'), 
            LPAD(CAST(POW(10, 10) - b.id AS STRING), 10, '0')
        )
    )
    FROM BoardJpaEntity b
    LEFT JOIN WhiskyJpaEntity w ON b.id = w.board.id
    WHERE b.boardType = 'BOARD'
    AND b.baseEntity.createdAt <= CURRENT_TIMESTAMP
    GROUP BY b.id
    HAVING CONCAT(
        LPAD(CAST(POW(10, 10) - COUNT(w) AS STRING), 10, '0'), 
        LPAD(CAST(POW(10, 10) - b.id AS STRING), 10, '0')
    ) < :cursor
    ORDER BY COUNT(w) DESC, b.baseEntity.createdAt DESC, b.id DESC
""")
    fun findRecentPopularBoards(@Param("cursor") cursor: String, pageable: Pageable): Page<BoardWithCursorValue>


    @Query(
        "SELECT b FROM BoardJpaEntity b " +
                "WHERE b.boardType = 'BOARD_PICTURE' " +
                "AND b.baseEntity.createdAt  <= CURRENT_TIMESTAMP " +
                "AND b.id < :cursorId " +
                "ORDER BY b.baseEntity.createdAt DESC"
    )
    fun findRecentBoardPictures(cursorId: Long, pageable: Pageable): Page<BoardJpaEntity>


}
package lyfe.lyfeBe.persistence.board

import lyfe.lyfeBe.board.BoardType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardJpaRepository : JpaRepository<BoardJpaEntity, Long> {

    @Query(
        "SELECT b " +
                "FROM BoardJpaEntity b " +
                "WHERE b.id < :cursorId " +
                "AND b.boardType = :type " +
                "AND (:date IS NULL OR b.baseEntity.createdAt = STR_TO_DATE(:date, '%Y-%m-%d'))" +
                "ORDER BY b.id DESC"
    )
    fun findByIdCursorId(cursorId: Long, date: String?, type: BoardType, page: Pageable): List<BoardJpaEntity>

    @Query(
        value = """
        SELECT b.id, b.created_at, b.updated_at, b.board_type, b.content, b.image_url, b.title, b.topic_id, b.user_id        FROM board b
        LEFT JOIN (
            SELECT board_id, COUNT(id) AS w_count
            FROM whisky
            GROUP BY board_id
        ) w ON b.id = w.board_id
        WHERE b.board_type = :type
        AND (:date IS NULL OR DATE(b.created_at) = STR_TO_DATE(:date, '%Y-%m-%d'))
        AND 
            (
             CONCAT(
                LPAD(POW(10, 10) - COALESCE(w.w_count, 0), 10, '0'),
                LPAD(POW(10, 10) - b.id, 10, '0')
            ) > :cursorId
            OR    COALESCE(w.w_count, 0) = 0
        )
        ORDER BY COALESCE(w.w_count, 0) DESC, b.id DESC
        LIMIT :count
        """, nativeQuery = true
    )
    fun findBoardsWithWhiskyCount(cursorId: String, count: Int, date: String?, type: BoardType): List<BoardJpaEntity>
}
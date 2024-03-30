package lyfe.lyfeBe.persistence.board

import lyfe.lyfeBe.board.BoardType
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface BoardJpaRepository : JpaRepository<BoardJpaEntity, Long> {
    @Query(
        value = """
        SELECT b.id, b.created_at, b.updated_at, b.board_type, b.content, b.image_url, b.title, b.topic_id, b.user_id
        FROM board b
        LEFT JOIN (
            SELECT board_id, COUNT(id) AS w_count
            FROM whisky
            GROUP BY board_id
        ) w ON b.id = w.board_id
        LEFT JOIN topic t
        ON b.topic_id = t.id
        WHERE b.board_type =  :#{#type.name()}
        AND t.id = :topicId
        AND (
            CONCAT(
                LPAD(POW(10, 10) - COALESCE(w.w_count, 0), 10, '0'),
                LPAD(POW(10, 10) - b.id, 10, '0')
            ) > :cursorId
            OR COALESCE(w.w_count, 0) = 0
        )
        ORDER BY COALESCE(w.w_count, 0) DESC, b.id DESC
        LIMIT :pageCount
    """, nativeQuery = true)
    fun findPopularBoardsWithWhisky(
        @Param("cursorId") cursorId: Long,
        @Param("type") type: BoardType,
        @Param("topicId") topicId: Long,
        @Param("pageCount") pageCount: Int,
    ): List<BoardJpaEntity>
    @Query(
        value = """
        SELECT b.id, b.created_at, b.updated_at, b.board_type, b.content, b.image_url, b.title, b.topic_id, b.user_id
        FROM board b
        LEFT JOIN (
            SELECT board_id, COUNT(id) AS c_count
            FROM comment
            GROUP BY board_id
        ) c ON b.id = c.board_id
        LEFT JOIN topic t
        ON b.topic_id = t.id
        WHERE b.board_type =  :#{#type.name()}
        AND t.id = :topicId
        AND 
            (
             CONCAT(
                LPAD(POW(10, 10) - COALESCE(c.c_count, 0), 10, '0'),
                LPAD(POW(10, 10) - b.id, 10, '0')
            ) > :cursorId
            OR    COALESCE(c.c_count, 0) = 0
        )
        ORDER BY COALESCE(c.c_count, 0) DESC, b.id DESC
        LIMIT :pageCount
        """, nativeQuery = true
    )
    fun findPopularBoardsWithComment(
        @Param("cursorId") cursorId: Long,
        @Param("type") type: BoardType,
        @Param("topicId") topicId: Long,
        @Param("pageCount") pageCount: Int,
    ): List<BoardJpaEntity>
    fun findAllByUserIdAndBoardTypeAndIdLessThanOrderByIdDesc(
        userId: Long,
        type: BoardType,
        cursorId: Long,
        pageable: Pageable
    ): List<BoardJpaEntity>

    fun findAllByBoardTypeAndTopicIdAndIdLessThanOrderByIdDesc(
        type: BoardType,
        topicId: Long?,
        cursorId: Long,
        pageable: Pageable
    ): List<BoardJpaEntity>
}
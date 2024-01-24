package lyfe.lyfeBe.persistence.board

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardJpaRepository : JpaRepository<BoardJpaEntity, Long> {

    @Query("SELECT t FROM BoardJpaEntity t WHERE t.id < :cursorId")
    fun findByIdCursorId(cursorId: Long, pageable: Pageable): Page<BoardJpaEntity>


    @Query(
        "SELECT b, COUNT(c), COUNT(w) FROM BoardJpaEntity b " +
                "LEFT JOIN CommentJpaEntity c ON b.id = c.board.id " +
                "LEFT JOIN WhiskyJpaEntity w ON b.id = w.board.id " +
                "WHERE b.baseEntity.createdAt = :today " +
                "GROUP BY b.id " +
                "ORDER BY COUNT(c) DESC, COUNT(w) DESC"
    )
    fun findPopularBoardsByTopicAndDate(): List<BoardJpaEntity>

}
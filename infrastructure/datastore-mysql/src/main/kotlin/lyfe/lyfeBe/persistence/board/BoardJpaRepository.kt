package lyfe.lyfeBe.persistence.board

import lyfe.lyfeBe.persistence.topic.TopicJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface BoardJpaRepository: JpaRepository<BoardJpaEntity, Long> {

    @Query("SELECT t FROM BoardJpaEntity t WHERE t.id < :cursorId")
    fun findByIdCursorId(boardId: Long, pageable: Pageable): Page<BoardJpaEntity>
}
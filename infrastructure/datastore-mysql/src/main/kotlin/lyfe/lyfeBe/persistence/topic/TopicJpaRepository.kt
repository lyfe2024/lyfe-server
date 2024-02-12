package lyfe.lyfeBe.persistence.topic

import lyfe.lyfeBe.topic.Topic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant
import java.time.LocalDateTime


interface TopicJpaRepository: JpaRepository<TopicJpaEntity, Long> {

    @Query("SELECT t FROM TopicJpaEntity t WHERE t.appliedAt < :date AND t.id < :cursorId")
    fun findPastTopics(date: Instant, @Param("cursorId") cursorId: Long?, pageable: Pageable): Page<TopicJpaEntity>

    @Query("SELECT t FROM TopicJpaEntity t WHERE FUNCTION('TO_CHAR', t.baseEntity.createdAt, 'YYYY-MM-DD') = FUNCTION('TO_CHAR', CURRENT_DATE, 'YYYY-MM-DD')")
    fun findByDate(): Topic
}
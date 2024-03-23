package lyfe.lyfeBe.persistence.topic

import lyfe.lyfeBe.topic.Topic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Optional


interface TopicJpaRepository: JpaRepository<TopicJpaEntity, Long> {

    @Query("SELECT t FROM TopicJpaEntity t WHERE t.appliedAt < :date AND t.id < :cursorId")
    fun findPastTopics(date: Instant, @Param("cursorId") cursorId: Long?, pageable: Pageable): Page<TopicJpaEntity>

    @Query("SELECT t FROM TopicJpaEntity t WHERE FUNCTION('DATE', t.baseEntity.createdAt) = :date")
    fun findByDate(@Param("date") date : LocalDate): Optional<TopicJpaEntity>
}
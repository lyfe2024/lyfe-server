package lyfe.lyfeBe.persistence.topic

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.util.Optional


interface TopicJpaRepository : JpaRepository<TopicJpaEntity, Long> {

    @Query("SELECT t FROM TopicJpaEntity t WHERE t.appliedAt < :date AND t.id < :cursorId")
    fun findPastTopics(
        @Param("date") date: String,
        @Param("cursorId") cursorId: Long,
        pageable: Pageable
    ): Page<TopicJpaEntity>
    @Query("SELECT t FROM TopicJpaEntity t WHERE FUNCTION('DATE', t.baseEntity.createdAt) = :date")
    fun findByDate(@Param("date") date: LocalDate): Optional<TopicJpaEntity>
}
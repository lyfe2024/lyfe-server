package lyfe.lyfeBe.persistence.topic

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate


interface TopicJpaRepository : JpaRepository<TopicJpaEntity, Long> {
    fun findByAppliedAt(appliedAt: LocalDate): TopicJpaEntity?
}
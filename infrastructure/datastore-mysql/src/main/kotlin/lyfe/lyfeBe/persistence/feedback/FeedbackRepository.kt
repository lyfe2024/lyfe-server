package lyfe.lyfeBe.persistence.feedback

import org.springframework.data.jpa.repository.JpaRepository

interface FeedbackRepository: JpaRepository<FeedbackJpaEntity, Long> {
}
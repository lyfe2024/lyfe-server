package lyfe.lyfeBe.persistence.feedback

import org.springframework.data.jpa.repository.JpaRepository

interface FeedbackRepository: JpaRepository<lyfe.lyfeBe.persistence.feedback.FeedbackJpaEntity, Long> {
}
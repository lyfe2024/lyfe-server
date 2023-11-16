package lyfe.lyfeBe.persistence.worry.comment

import org.springframework.data.jpa.repository.JpaRepository

interface WorryCommentRepository: JpaRepository<lyfe.lyfeBe.persistence.worry.comment.WorryCommentJpaEntity, Long> {
}
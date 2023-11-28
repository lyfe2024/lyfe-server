package lyfe.lyfeBe.persistence.comment

import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<CommentJpaEntity, Long> {
}
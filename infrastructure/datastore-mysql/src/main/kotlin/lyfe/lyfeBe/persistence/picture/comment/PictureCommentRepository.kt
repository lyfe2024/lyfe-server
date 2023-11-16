package lyfe.lyfeBe.persistence.picture.comment

import org.springframework.data.jpa.repository.JpaRepository

interface PictureCommentRepository: JpaRepository<lyfe.lyfeBe.persistence.picture.comment.PictureCommentJpaEntity, Long> {
}
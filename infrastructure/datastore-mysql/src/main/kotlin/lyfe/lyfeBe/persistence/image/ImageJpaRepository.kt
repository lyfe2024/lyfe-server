package lyfe.lyfeBe.persistence.image

import org.springframework.data.jpa.repository.JpaRepository

interface ImageJpaRepository : JpaRepository<ImageJpaEntity, Long> {
}
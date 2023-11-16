package lyfe.lyfeBe.persistence.picture.whisky

import org.springframework.data.jpa.repository.JpaRepository

interface PictureWhiskyRepository: JpaRepository<lyfe.lyfeBe.persistence.picture.whisky.PictureWhiskyJpaEntity, Long> {
}
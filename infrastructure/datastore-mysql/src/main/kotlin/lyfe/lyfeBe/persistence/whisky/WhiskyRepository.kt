package lyfe.lyfeBe.persistence.whisky

import org.springframework.data.jpa.repository.JpaRepository

interface WhiskyRepository: JpaRepository<WhiskyJpaEntity, Long> {
}
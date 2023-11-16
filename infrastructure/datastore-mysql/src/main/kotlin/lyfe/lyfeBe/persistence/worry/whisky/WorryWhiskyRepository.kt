package lyfe.lyfeBe.persistence.worry.whisky

import org.springframework.data.jpa.repository.JpaRepository

interface WorryWhiskyRepository: JpaRepository<lyfe.lyfeBe.persistence.worry.whisky.WorryWhiskyJpaEntity, Long> {
}
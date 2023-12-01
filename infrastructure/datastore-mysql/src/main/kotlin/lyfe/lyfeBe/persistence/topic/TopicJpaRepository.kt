package lyfe.lyfeBe.persistence.topic

import org.springframework.data.jpa.repository.JpaRepository

interface TopicJpaRepository: JpaRepository<TopicJpaEntity, Long> {
}
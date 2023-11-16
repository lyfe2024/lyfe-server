package lyfe.lyfeBe.persistence.topic

import org.springframework.data.jpa.repository.JpaRepository

interface TopicRepository: JpaRepository<lyfe.lyfeBe.persistence.topic.TopicJpaEntity, Long> {
}
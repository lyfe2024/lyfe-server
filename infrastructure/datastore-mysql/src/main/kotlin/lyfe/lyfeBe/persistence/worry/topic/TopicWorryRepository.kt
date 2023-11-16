package lyfe.lyfeBe.persistence.worry.topic

import org.springframework.data.jpa.repository.JpaRepository

interface TopicWorryRepository: JpaRepository<lyfe.lyfeBe.persistence.worry.topic.TopicWorryJpaEntity, Long> {
}
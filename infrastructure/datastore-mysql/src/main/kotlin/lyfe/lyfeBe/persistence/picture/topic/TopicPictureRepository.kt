package lyfe.lyfeBe.persistence.picture.topic

import org.springframework.data.jpa.repository.JpaRepository

interface TopicPictureRepository: JpaRepository<lyfe.lyfeBe.persistence.picture.topic.TopicPictureJpaEntity, Long> {
}
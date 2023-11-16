package lyfe.lyfeBe.persistence.topic

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.picture.topic.TopicPictureJpaEntity
import lyfe.lyfeBe.persistence.worry.topic.TopicWorryJpaEntity
import java.time.Instant

@Entity
@Table(name = "topic")
class TopicJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    var id: Long = 0,
    var content: String,
    var appliedAt: Instant? = null,

    ) : BaseEntity() {
}
package lyfe.lyfeBe.persistence.topic

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.topic.Topic
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "topic")
class TopicJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val content: String,

    @Embedded
    val baseEntity: BaseEntity = BaseEntity()
) {
    fun toDomain(): Topic {
        return Topic(
            id = id,
            content = content,
            createdAt = baseEntity.createdAt,
            updatedAt = baseEntity.updatedAt,
            visibility = baseEntity.visibility
        )
    }

    companion object {
        fun from(topic: Topic): TopicJpaEntity = TopicJpaEntity(
            id = topic.id,
            content = topic.content
        )

    }
}
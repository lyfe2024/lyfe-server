package lyfe.lyfeBe.persistence.topic

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.topic.Topic
import org.jetbrains.annotations.NotNull
import java.time.Instant

@Entity
@Table(name = "topic")
class TopicJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotNull
    val content: String,

    @field:NotNull
    val appliedAt: Instant? = null,

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
            content = topic.content,
            baseEntity = BaseEntity(
                createdAt = topic.createdAt,
                updatedAt = topic.updatedAt,
                visibility = topic.visibility
            )
        )
    }
}
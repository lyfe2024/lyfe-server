package lyfe.lyfeBe.persistence.worry.topic

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.BaseEntity

import lyfe.lyfeBe.persistence.topic.TopicJpaEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import java.time.Instant

@Entity
@Table(name = "topic_worry")
class TopicWorryJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_worry_id")
    var id: Long = 0,
    var title: String,
    var content: String,
    var deletedAt: Instant? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: UserJpaEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    var topic: TopicJpaEntity,

    ) : BaseEntity() {
}
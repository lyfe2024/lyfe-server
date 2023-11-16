package lyfe.lyfeBe.persistence.worry.comment

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import lyfe.lyfeBe.persistence.worry.topic.TopicWorryJpaEntity
import java.time.Instant

@Entity
@Table(name = "worry_comment")
class WorryCommentJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worry_comment_id")
    var id: Long = 0,
    var content: String,
    var depth: Int,
    var sequence: Int,
    var deletedAt: Instant? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: UserJpaEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    var topicWorry: TopicWorryJpaEntity,

    ) : BaseEntity() {
}
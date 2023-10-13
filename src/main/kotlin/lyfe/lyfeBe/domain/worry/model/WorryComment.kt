package lyfe.lyfeBe.domain.worry.model

import jakarta.persistence.*
import lyfe.lyfeBe.common.model.BaseEntity
import lyfe.lyfeBe.domain.user.model.User
import java.time.Instant

@Entity
class WorryComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worry_comment_id")
    var id: Long = 0,
    var content: String,
    var depth: Int,
    var sequence: Int,
    var deletedAt: Instant? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    var topicWorry: TopicWorry,

    ) : BaseEntity() {
}
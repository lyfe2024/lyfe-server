package lyfe.lyfeBe.persistence.picture.comment

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.picture.topic.TopicPictureJpaEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import java.time.Instant

@Entity
@Table(name = "picture_comment")
class PictureCommentJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_comment_id")
    var id: Long = 0,
    var content: String,
    var depth: Int,
    var sequence: Int,
    var deletedAt: Instant? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: UserJpaEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    var topicPicture: TopicPictureJpaEntity,

    ) : BaseEntity() {
}
package lyfe.lyfeBe.persistence.picture.topic

import jakarta.persistence.*
import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.image.ImageListConverter
import lyfe.lyfeBe.persistence.topic.TopicJpaEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import java.time.Instant

@Entity
@Table(name = "topic_picture")
class TopicPictureJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_picture_id")
    var id: Long = 0,

    @Convert(converter = ImageListConverter::class)
    @Column(columnDefinition = "json")
    var picture: Image,
    var title: String,
    var deletedAt: Instant? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: UserJpaEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    var topic: TopicJpaEntity,

    ) : BaseEntity() {
}
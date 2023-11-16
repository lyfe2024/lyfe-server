package lyfe.lyfeBe.persistence.picture.whisky

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.picture.topic.TopicPictureJpaEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(name = "picture_whisky")
class PictureWhiskyJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_whisky_id")
    var id: Long = 0,
    @CreatedDate
    var createdAt: Instant? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: UserJpaEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    var topicPicture: TopicPictureJpaEntity,

    ) {
}
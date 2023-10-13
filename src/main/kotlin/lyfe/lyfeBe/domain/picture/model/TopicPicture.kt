package lyfe.lyfeBe.domain.picture.model

import jakarta.persistence.*
import lyfe.lyfeBe.common.model.BaseEntity
import lyfe.lyfeBe.domain.topic.model.Topic
import lyfe.lyfeBe.domain.user.model.User
import java.time.Instant

@Entity
class TopicPicture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_picture_id")
    var id: Long = 0,
    var picture: String,
    var title: String,
    var deletedAt: Instant? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    var topic: Topic,

    @OneToMany(mappedBy = "topicPicture", cascade = [CascadeType.ALL], orphanRemoval = true)
    var pictureCommentList: MutableList<PictureComment> = mutableListOf(),
    @OneToMany(mappedBy = "topicPicture", cascade = [CascadeType.ALL], orphanRemoval = true)
    var pictureWhiskyList: MutableList<PictureWhisky> = mutableListOf(),

    ) : BaseEntity() {
}
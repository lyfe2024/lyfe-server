package lyfe.lyfeBe.domain.topic.model

import jakarta.persistence.*
import lyfe.lyfeBe.common.model.BaseEntity
import lyfe.lyfeBe.domain.picture.model.TopicPicture
import lyfe.lyfeBe.domain.worry.model.TopicWorry
import java.time.Instant

@Entity
class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    var id: Long = 0,
    var content: String,
    var appliedAt: Instant? = null,

    @OneToMany(mappedBy = "topic", cascade = [CascadeType.ALL], orphanRemoval = true)
    var topicPictureList: MutableList<TopicPicture> = mutableListOf(),
    @OneToMany(mappedBy = "topic", cascade = [CascadeType.ALL], orphanRemoval = true)
    var topicWorryList: MutableList<TopicWorry> = mutableListOf(),

) : BaseEntity() {
}
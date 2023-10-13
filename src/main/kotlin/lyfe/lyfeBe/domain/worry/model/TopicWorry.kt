package lyfe.lyfeBe.domain.worry.model

import jakarta.persistence.*
import lyfe.lyfeBe.common.model.BaseEntity

import lyfe.lyfeBe.domain.topic.model.Topic
import lyfe.lyfeBe.domain.user.model.User
import java.time.Instant

@Entity
class TopicWorry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_worry_id")
    var id: Long = 0,
    var title: String,
    var content: String,
    var deletedAt: Instant? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    var topic: Topic,

    @OneToMany(mappedBy = "topicWorry", cascade = [CascadeType.ALL], orphanRemoval = true)
    var worryCommentList: MutableList<WorryComment> = mutableListOf(),
    @OneToMany(mappedBy = "topicWorry", cascade = [CascadeType.ALL], orphanRemoval = true)
    var worryWhiskyList: MutableList<WorryWhisky> = mutableListOf(),

    ) : BaseEntity() {
}
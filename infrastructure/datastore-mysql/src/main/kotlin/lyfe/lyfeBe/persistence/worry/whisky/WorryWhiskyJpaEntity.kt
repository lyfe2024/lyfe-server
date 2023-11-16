package lyfe.lyfeBe.persistence.worry.whisky

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import lyfe.lyfeBe.persistence.worry.topic.TopicWorryJpaEntity
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(name = "worry_whisky")
class WorryWhiskyJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worry_whisky_id")
    var id: Long = 0,
    @CreatedDate
    var createdAt: Instant? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: UserJpaEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    var topicWorry: TopicWorryJpaEntity,

    ) {
}
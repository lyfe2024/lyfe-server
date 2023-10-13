package lyfe.lyfeBe.domain.worry.model

import jakarta.persistence.*
import lyfe.lyfeBe.domain.user.model.User
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
class WorryWhisky(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worry_whisky_id")
    var id: Long = 0,
    @CreatedDate
    var createdAt: Instant? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    var topicWorry: TopicWorry,

    ) {
}
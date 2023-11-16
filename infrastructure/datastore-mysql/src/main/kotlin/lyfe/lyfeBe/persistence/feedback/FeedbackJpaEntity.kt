package lyfe.lyfeBe.persistence.feedback

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(name = "feedback")
class FeedbackJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    var id: Long = 0,
    var content: String,
    var checked: Boolean,
    @CreatedDate
    var createdAt: Instant,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: UserJpaEntity
) {
}
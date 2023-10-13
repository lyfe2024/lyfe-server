package lyfe.lyfeBe.domain.feedback.model

import jakarta.persistence.*
import lyfe.lyfeBe.domain.user.model.User
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
class Feedback(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    var id: Long = 0,
    var content: String,
    var checked: Boolean,
    @CreatedDate
    var createdAt: Instant,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: User
) {
}
package lyfe.lyfeBe.persistence.feedback

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(name = "feedback")
class FeedbackJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotNull
    val content: String,

    @field:NotNull
    @Column(columnDefinition = "boolean default false")
    val checked: Boolean = false,

    @field:NotNull
    @CreatedDate
    val createdAt: Instant,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_feedback_user_id"))
    val user: UserJpaEntity
) {
}
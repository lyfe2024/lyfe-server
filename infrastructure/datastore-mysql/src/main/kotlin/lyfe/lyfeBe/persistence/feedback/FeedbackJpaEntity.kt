package lyfe.lyfeBe.persistence.feedback

import jakarta.persistence.*
import lyfe.lyfeBe.feedback.Feedback
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import org.jetbrains.annotations.NotNull
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
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

    @Embedded
    val baseEntity: BaseEntity = BaseEntity(),

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_feedback_user_id"))
    val user: UserJpaEntity
) {
    fun toDomain() =
        Feedback(
            id = id,
            content = content,
            checked = checked,
            user = user.toDomain()
        )

    companion object {
        fun from(feedBack: Feedback): FeedbackJpaEntity {
            return FeedbackJpaEntity(
                content = feedBack.content,
                user = UserJpaEntity.from(feedBack.user)
            )

        }
    }
}
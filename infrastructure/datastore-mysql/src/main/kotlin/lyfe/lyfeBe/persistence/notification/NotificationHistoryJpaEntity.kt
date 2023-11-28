package lyfe.lyfeBe.persistence.notification

import jakarta.persistence.*
import lyfe.lyfeBe.notification.NotificationType
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "notification_history")
class NotificationHistoryJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotNull
    val content: String,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    val notificationType: NotificationType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_notification_history_user_id"))
    val user: UserJpaEntity,

    @Embedded
    val baseEntity: BaseEntity = BaseEntity()

) {
}
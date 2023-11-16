package lyfe.lyfeBe.persistence.notification

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import java.time.Instant

@Entity
@Table(name = "notification_history")
class NotificationHistoryJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_history_id")
    var id: Long = 0,
    var content: String,
    var notificationType: String,
    var notifiedAt: Instant,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: UserJpaEntity

) : BaseEntity() {
}
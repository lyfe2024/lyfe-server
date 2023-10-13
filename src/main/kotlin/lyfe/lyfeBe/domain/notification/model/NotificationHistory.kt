package lyfe.lyfeBe.domain.notification.model

import jakarta.persistence.*
import lyfe.lyfeBe.common.model.BaseEntity
import lyfe.lyfeBe.domain.user.model.User
import java.time.Instant

@Entity
class NotificationHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_history_id")
    var id: Long = 0,
    var content: String,
    var notificationType: String,
    var notifiedAt: Instant,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: User

) : BaseEntity() {
}
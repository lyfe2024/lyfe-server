package lyfe.lyfeBe.persistence.user

import jakarta.persistence.*
import lyfe.lyfeBe.image.domain.Image
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.image.ImageListConverter
import lyfe.lyfeBe.user.domain.Role
import lyfe.lyfeBe.user.domain.UserStatus
import java.time.Instant

@Entity
@Table(name = "user")
class UserJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long = 0,
    var email: String,
    var hashedPassword: String,
    var nickname: String,
    var notificationConsent: Boolean,
    var fcmRegistration: Boolean,

    @Convert(converter = ImageListConverter::class)
    @Column(columnDefinition = "json")
    var profileImage: Image? = null,

    var withdrawnAt: Instant? = null,

    @Enumerated(EnumType.STRING)
    var userStatus: UserStatus,

    @Enumerated(EnumType.STRING)
    var role: Role,

    ) : BaseEntity() {
}
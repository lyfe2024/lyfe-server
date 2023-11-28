package lyfe.lyfeBe.persistence.user

import jakarta.persistence.*
import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.image.ImageListConverter
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.UserStatus
import org.jetbrains.annotations.NotNull
import java.time.Instant

@Entity
@Table(name = "user")
class UserJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotNull
    val email: String,

    @field:NotNull
    val hashedPassword: String,

    @field:NotNull
    val nickname: String,

    val notificationConsent: Boolean,
    val fcmRegistration: Boolean,

    @Convert(converter = ImageListConverter::class)
    @Column(columnDefinition = "json")
    val profileImage: Image? = null,

    val withdrawnAt: Instant? = null,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    val userStatus: UserStatus,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    val role: Role,

    @Embedded
    val baseEntity: BaseEntity = BaseEntity()
) {
}
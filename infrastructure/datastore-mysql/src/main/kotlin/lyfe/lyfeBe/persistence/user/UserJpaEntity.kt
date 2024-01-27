package lyfe.lyfeBe.persistence.user

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import org.jetbrains.annotations.NotNull
import java.time.Instant

@Entity
@Table(name = "user")
class UserJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val profileUrl : String,

    @field:NotNull
    val email: String,

    @field:NotNull
    val hashedPassword: String,

    @field:NotNull
    val nickname: String,

    val notificationConsent: Boolean,

    val fcmRegistration: Boolean,

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
    fun toDomain(): User {
        return User(
            id = id,
            email = email,
            hashedPassword = hashedPassword,
            nickname = nickname,
            profileUrl = profileUrl,
            notificationConsent = notificationConsent,
            fcmRegistration = fcmRegistration,
            withdrawnAt = withdrawnAt,
            userStatus = userStatus,
            role = role,
            createdAt = baseEntity.createdAt,
            updatedAt = baseEntity.updatedAt
        )

    }

    companion object {
        fun from(user: User): UserJpaEntity = UserJpaEntity(
            id = user.id,
            email = user.email,
            hashedPassword = user.hashedPassword,
            nickname = user.nickname,
            notificationConsent = user.notificationConsent,
            profileUrl = user.profileUrl,
            fcmRegistration = user.fcmRegistration,
            withdrawnAt = user.withdrawnAt,
            userStatus = user.userStatus,
            role = user.role,
            baseEntity = BaseEntity(
                createdAt = user.createdAt,
                updatedAt = user.updatedAt
            )
        )
    }
}
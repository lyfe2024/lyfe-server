package lyfe.lyfeBe.persistence.auth

import jakarta.persistence.*
import lyfe.lyfeBe.auth.RefreshToken
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import java.time.Instant


@Entity
@Table(name = "refresh_token")
class RefreshTokenJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val refreshToken: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_refresh_token_user_id"))
    val user: UserJpaEntity,

    @CreatedDate
    val createdAt: Instant? = null,

    @UpdateTimestamp
    val updatedAt: Instant? = null,
) {
    fun toDomain(): RefreshToken {
        return RefreshToken(
            id = id,
            refreshToken = refreshToken,
            user = user.toDomain(),
        )
    }

    companion object {
        fun from(refreshToken: RefreshToken) =
            RefreshTokenJpaEntity(
                id = refreshToken.id,
                refreshToken = refreshToken.refreshToken,
                user = UserJpaEntity.from(refreshToken.user),
            )

        fun update(refreshToken: RefreshToken) =
            RefreshTokenJpaEntity(
                id = refreshToken.id,
                refreshToken = refreshToken.refreshToken,
                user = UserJpaEntity.from(refreshToken.user),
            )
    }

}



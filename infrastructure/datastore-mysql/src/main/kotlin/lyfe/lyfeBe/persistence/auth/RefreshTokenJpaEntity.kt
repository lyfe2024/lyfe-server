package lyfe.lyfeBe.persistence.auth

import jakarta.persistence.*
import lyfe.lyfeBe.auth.RefreshToken
import lyfe.lyfeBe.auth.TokenStatus
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant


@Entity
@Table(name = "refresh_token")
class RefreshTokenJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var refreshToken: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_refresh_token_user_id"))
    val user: UserJpaEntity,

    @CreatedDate
    @LastModifiedDate
    val createdAt: Instant? = null,

    @LastModifiedDate
    val updatedAt: Instant? = null,

    @Column(name = "expired_at", columnDefinition = "Datetime", scale = 6)
    val expiredAt: Instant? = null,

    @Enumerated(EnumType.STRING)
    val tokenStatus: TokenStatus = TokenStatus.PERMANENT
) {
    fun toDomain(): RefreshToken {
        return RefreshToken(
            id = id,
            refreshToken = refreshToken,
            user = user.toDomain(),
            expiredAt = expiredAt?: Instant.now().plusSeconds(86400000),
            tokenStatus = tokenStatus
        )
    }
    fun update(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    companion object {
        fun from(refreshToken: RefreshToken) = RefreshTokenJpaEntity(
            id = refreshToken.id,
            refreshToken = refreshToken.refreshToken,
            user = UserJpaEntity.from(refreshToken.user)
        )
    }

}



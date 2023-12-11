package lyfe.lyfeBe.persistence.whisky

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.board.BoardJpaEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import lyfe.lyfeBe.whisky.Whisky
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(name = "whisky")
class WhiskyJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @CreatedDate
    val createdAt: Instant? = null,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_whisky_user_id"))
    val user: UserJpaEntity,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", foreignKey = ForeignKey(name = "fk_whisky_board_id"))
    val board: BoardJpaEntity,

    ) {
    fun toDomain(): Whisky {
        return Whisky(
            id = id,
            user = user.toDomain(),
            board = board.toDomain(),
            createdAt = createdAt
        )
    }

    companion object {
        fun from(whisky: Whisky): WhiskyJpaEntity {
            return WhiskyJpaEntity(
                user = UserJpaEntity.from(whisky.user),
                board = BoardJpaEntity.from(whisky.board),
                createdAt = whisky.createdAt
            )

        }
    }
}
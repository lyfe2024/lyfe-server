package lyfe.lyfeBe.persistence.whisky

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.board.BoardJpaEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
@Table(name = "whisky")
class WhiskyJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "whisky_id")
    val id: Long = 0,

    @CreatedDate
    val createdAt: Instant? = null,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    val user: UserJpaEntity,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    val board: BoardJpaEntity,

    ) {
}
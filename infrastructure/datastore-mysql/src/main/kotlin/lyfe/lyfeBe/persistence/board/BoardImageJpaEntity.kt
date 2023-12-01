package lyfe.lyfeBe.persistence.board

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.board.BoardJpaEntity
import org.jetbrains.annotations.NotNull
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "board_image")
class BoardImageJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotNull
    val url: String,

    val width: Int,

    val height: Int,

    @ManyToOne
    @JoinColumn(name = "board_id")
    val Board: BoardJpaEntity,

    @Embedded
    val baseEntity: BaseEntity

) {

}

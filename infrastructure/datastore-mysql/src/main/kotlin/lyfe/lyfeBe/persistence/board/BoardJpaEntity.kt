package lyfe.lyfeBe.persistence.board

import jakarta.persistence.*
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.image.ImageListConverter
import lyfe.lyfeBe.persistence.topic.TopicJpaEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import org.jetbrains.annotations.NotNull
import java.time.Instant

@Entity
@Table(name = "board")
class BoardJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotNull
    val title: String,

    val content: String? = null,

    @Convert(converter = ImageListConverter::class)
    @Column(columnDefinition = "json")
    val picture: Image? = null,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    val boardType: BoardType,

    val deletedAt: Instant? = null,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_board_user_id"))
    val user: UserJpaEntity,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", foreignKey = ForeignKey(name = "fk_board_topic_id"))
    val topic: TopicJpaEntity

    ) : BaseEntity() {
}
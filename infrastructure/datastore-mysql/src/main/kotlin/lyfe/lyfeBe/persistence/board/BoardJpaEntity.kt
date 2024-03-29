package lyfe.lyfeBe.persistence.board

import jakarta.persistence.*
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.topic.TopicJpaEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "board")
class BoardJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotNull
    val title: String,

    val content: String,


    @field:NotNull
    @Enumerated(EnumType.STRING)
    val boardType: BoardType,

    @Column(name = "image_url")
    val imageUrl: String? = null,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_board_user_id"))
    val user: UserJpaEntity,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", foreignKey = ForeignKey(name = "fk_board_topic_id"))
    val topic: TopicJpaEntity,

    @Embedded
    val baseEntity: BaseEntity = BaseEntity()

) {
    fun toDomain(): Board {
        return Board(
            id = id,
            title = title,
            content = content,
            boardType = boardType,
            imageUrl = imageUrl,
            user = user.toDomain(),
            topic = topic.toDomain(),
            createdAt = baseEntity.createdAt,
            updatedAt = baseEntity.updatedAt
        )
    }

    companion object {

        fun from(board: Board) =
            BoardJpaEntity(
                id = board.id,
                title = board.title,
                content = board.content,
                boardType = board.boardType,
                imageUrl = board.imageUrl,
                user = UserJpaEntity.from(board.user),
                topic = TopicJpaEntity.from(board.topic),
                baseEntity = BaseEntity(
                    createdAt = board.createdAt,
                    updatedAt = board.updatedAt
                )
            )

        fun update(board: Board) =
            BoardJpaEntity(
                id = board.id,
                title = board.title,
                content = board.content,
                imageUrl = board.imageUrl,
                boardType = board.boardType,
                user = UserJpaEntity.from(board.user),
                topic = TopicJpaEntity.from(board.topic),
                baseEntity = BaseEntity(
                    createdAt = board.createdAt,
                    updatedAt = board.updatedAt
                )
            )
    }
}
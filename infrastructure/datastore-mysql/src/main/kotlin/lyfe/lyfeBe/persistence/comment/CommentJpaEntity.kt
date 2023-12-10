package lyfe.lyfeBe.persistence.comment

import jakarta.persistence.*
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.board.BoardJpaEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import org.jetbrains.annotations.NotNull
import java.time.Instant

@Entity
@Table(name = "comment")
class CommentJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotNull
    val content: String,

    val commentGroupId: Long? = null,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    val user: UserJpaEntity,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    val board: BoardJpaEntity,

    @Embedded
    val baseEntity: BaseEntity = BaseEntity()

) {
    fun toDomain() =
        Comment(
            id = id,
            content = content,
            commentGroupId = commentGroupId,
            user = user.toDomain(),
            board = board.toDomain(),
            createdAt = baseEntity.createdAt,
            updatedAt = baseEntity.updatedAt,
            visibility = baseEntity.visibility,
        )

    companion object {
        fun from(comment: Comment) =
            CommentJpaEntity(
                id = comment.id,
                content = comment.content,
                commentGroupId = comment.commentGroupId,
                user = UserJpaEntity.from(comment.user),
                board = BoardJpaEntity.from(comment.board),
                baseEntity = BaseEntity(),
            )
    }
}
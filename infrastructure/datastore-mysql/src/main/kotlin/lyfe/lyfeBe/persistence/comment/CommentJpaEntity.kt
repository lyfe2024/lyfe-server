package lyfe.lyfeBe.persistence.comment

import jakarta.persistence.*
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.board.BoardJpaEntity
import lyfe.lyfeBe.persistence.board.BoardMapper
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import lyfe.lyfeBe.persistence.user.UserMapper
import org.jetbrains.annotations.NotNull

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
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_comment_user_id"))
    val user: UserJpaEntity,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", foreignKey = ForeignKey(name = "fk_comment_board_id"))
    val board: BoardJpaEntity,

    @Embedded
    val baseEntity: BaseEntity = BaseEntity()

) {
    fun toDomain(): Comment =
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
        fun from(comment: Comment): CommentJpaEntity =
            CommentJpaEntity(
                id = comment.id,
                content = comment.content,
                commentGroupId = comment.commentGroupId,
                user = UserMapper.mapToJpaEntity(comment.user),
                board = BoardMapper.mapToJpaEntity(comment.board),
                baseEntity = BaseEntity(
                    createdAt = comment.createdAt,
                    updatedAt = comment.updatedAt,
                    visibility = comment.visibility
                )
            )
    }


}
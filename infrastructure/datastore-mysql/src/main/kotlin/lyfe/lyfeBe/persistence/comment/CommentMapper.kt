package lyfe.lyfeBe.persistence.comment

import lyfe.lyfeBe.persistence.board.BoardMapper
import lyfe.lyfeBe.persistence.user.UserMapper
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.persistence.BaseEntity

object CommentMapper {

    fun mapToDomainEntity(comment: CommentJpaEntity): Comment =
        Comment(
            id = comment.id,
            content = comment.content,
            commentGroupId = comment.commentGroupId,
            user = UserMapper.mapToDomainEntity(comment.user),
            board = BoardMapper.mapToDomainEntity(comment.board),
            createdAt = comment.baseEntity.createdAt,
            updatedAt = comment.baseEntity.updatedAt,
            deletedAt = comment.deletedAt,
            visibility = comment.baseEntity.visibility,
        )

    fun mapToJpaEntity(comment: Comment): CommentJpaEntity =
        CommentJpaEntity(
            id = comment.id,
            content = comment.content,
            commentGroupId = comment.commentGroupId,
            user = UserMapper.mapToJpaEntity(comment.user),
            board = BoardMapper.mapToJpaEntity(comment.board),
            deletedAt = comment.deletedAt,
            baseEntity = BaseEntity(
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt,
                visibility = comment.visibility
            )
        )
}

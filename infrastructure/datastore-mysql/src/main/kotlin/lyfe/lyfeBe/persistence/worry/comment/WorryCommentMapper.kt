package lyfe.lyfeBe.persistence.worry.comment

import lyfe.lyfeBe.persistence.user.UserMapper
import lyfe.lyfeBe.persistence.worry.topic.TopicWorryMapper
import lyfe.lyfeBe.worry.WorryComment

object WorryCommentMapper {

        fun mapToDomainEntity(worryComment: WorryCommentJpaEntity): WorryComment =
            WorryComment(
                id = worryComment.id,
                content = worryComment.content,
                depth = worryComment.depth,
                sequence = worryComment.sequence,
                user = UserMapper.mapToDomainEntity(worryComment.user),
                topicWorry = TopicWorryMapper.mapToDomainEntity(worryComment.topicWorry),
                createdAt = worryComment.createdAt,
                updatedAt = worryComment.updatedAt,
                deletedAt = worryComment.deletedAt,
                visibility = worryComment.visibility,
            )

        fun mapToJpaEntity(worryComment: WorryComment): WorryCommentJpaEntity =
            WorryCommentJpaEntity(
                id = worryComment.id,
                content = worryComment.content,
                depth = worryComment.depth,
                sequence = worryComment.sequence,
                user = UserMapper.mapToJpaEntity(worryComment.user),
                topicWorry = TopicWorryMapper.mapToJpaEntity(worryComment.topicWorry),
            ).apply {
                createdAt = worryComment.createdAt
                updatedAt = worryComment.updatedAt
                deletedAt = worryComment.deletedAt
                visibility = worryComment.visibility
            }
}
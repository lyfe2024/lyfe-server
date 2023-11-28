package lyfe.lyfeBe.persistence.worry.topic

import lyfe.lyfeBe.persistence.topic.TopicMapper
import lyfe.lyfeBe.persistence.user.UserMapper
import lyfe.lyfeBe.worry.TopicWorry

object TopicWorryMapper {

    fun mapToDomainEntity(topicWorry: TopicWorryJpaEntity): TopicWorry =
        TopicWorry(
            id = topicWorry.id,
            title = topicWorry.title,
            content = topicWorry.content,
            user = UserMapper.mapToDomainEntity(topicWorry.user),
            topic = TopicMapper.mapToDomainEntity(topicWorry.topic),
            createdAt = topicWorry.createdAt,
            updatedAt = topicWorry.updatedAt,
            deletedAt = topicWorry.deletedAt,
            visibility = topicWorry.visibility,
        )

    fun mapToJpaEntity(topicWorry: TopicWorry): TopicWorryJpaEntity =
        TopicWorryJpaEntity(
            id = topicWorry.id,
            title = topicWorry.title,
            content = topicWorry.content,
            user = UserMapper.mapToJpaEntity(topicWorry.user),
            topic = TopicMapper.mapToJpaEntity(topicWorry.topic),
        ).apply {
            createdAt = topicWorry.createdAt
            updatedAt = topicWorry.updatedAt
            deletedAt = topicWorry.deletedAt
            visibility = topicWorry.visibility
        }
}
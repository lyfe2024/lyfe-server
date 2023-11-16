package lyfe.lyfeBe.persistence.picture.topic

import lyfe.lyfeBe.persistence.topic.TopicMapper
import lyfe.lyfeBe.persistence.user.UserMapper
import lyfe.lyfeBe.picture.domain.TopicPicture

object TopicPictureMapper {

    fun mapToDomainEntity(topicPicture: TopicPictureJpaEntity): TopicPicture =
        TopicPicture(
            id = topicPicture.id,
            picture = topicPicture.picture,
            title = topicPicture.title,
            user = UserMapper.mapToDomainEntity(topicPicture.user),
            topic = TopicMapper.mapToDomainEntity(topicPicture.topic),
            createdAt = topicPicture.createdAt,
            updatedAt = topicPicture.updatedAt,
            deletedAt = topicPicture.deletedAt,
            visibility = topicPicture.visibility,
        )

    fun mapToJpaEntity(topicPicture: TopicPicture): TopicPictureJpaEntity =
        TopicPictureJpaEntity(
            id = topicPicture.id,
            picture = topicPicture.picture,
            title = topicPicture.title,
            user = UserMapper.mapToJpaEntity(topicPicture.user),
            topic = TopicMapper.mapToJpaEntity(topicPicture.topic),
        ).apply {
            createdAt = topicPicture.createdAt
            updatedAt = topicPicture.updatedAt
            deletedAt = topicPicture.deletedAt
            visibility = topicPicture.visibility
        }
}
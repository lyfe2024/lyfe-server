package lyfe.lyfeBe.persistence.picture.whisky

import lyfe.lyfeBe.persistence.picture.topic.TopicPictureMapper
import lyfe.lyfeBe.persistence.user.UserMapper
import lyfe.lyfeBe.picture.domain.PictureWhisky

object PictureWhiskyMapper {

    fun mapToDomainEntity(pictureWhisky: PictureWhiskyJpaEntity): PictureWhisky =
        PictureWhisky(
            id = pictureWhisky.id,
            user = UserMapper.mapToDomainEntity(pictureWhisky.user),
            topicPicture = TopicPictureMapper.mapToDomainEntity(pictureWhisky.topicPicture),
            createdAt = pictureWhisky.createdAt,
        )

    fun mapToJpaEntity(pictureWhisky: PictureWhisky): PictureWhiskyJpaEntity =
        PictureWhiskyJpaEntity(
            id = pictureWhisky.id,
            user = UserMapper.mapToJpaEntity(pictureWhisky.user),
            topicPicture = TopicPictureMapper.mapToJpaEntity(pictureWhisky.topicPicture),
            createdAt = pictureWhisky.createdAt
        )
}

package lyfe.lyfeBe.persistence.picture.comment

import lyfe.lyfeBe.persistence.picture.topic.TopicPictureMapper
import lyfe.lyfeBe.persistence.user.UserMapper
import lyfe.lyfeBe.picture.PictureComment

object PictureCommentMapper {

    fun mapToDomainEntity(pictureComment: PictureCommentJpaEntity): PictureComment =
        PictureComment(
            id = pictureComment.id,
            content = pictureComment.content,
            depth = pictureComment.depth,
            sequence = pictureComment.sequence,
            user = UserMapper.mapToDomainEntity(pictureComment.user),
            topicPicture = TopicPictureMapper.mapToDomainEntity(pictureComment.topicPicture),
            createdAt = pictureComment.createdAt,
            updatedAt = pictureComment.updatedAt,
            deletedAt = pictureComment.deletedAt,
            visibility = pictureComment.visibility,
        )

    fun mapToJpaEntity(pictureComment: PictureComment): PictureCommentJpaEntity =
        PictureCommentJpaEntity(
            id = pictureComment.id,
            content = pictureComment.content,
            depth = pictureComment.depth,
            sequence = pictureComment.sequence,
            user = UserMapper.mapToJpaEntity(pictureComment.user),
            topicPicture = TopicPictureMapper.mapToJpaEntity(pictureComment.topicPicture),
        ).apply {
            createdAt = pictureComment.createdAt
            updatedAt = pictureComment.updatedAt
            deletedAt = pictureComment.deletedAt
            visibility = pictureComment.visibility
        }
}
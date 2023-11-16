package lyfe.lyfeBe.picture.domain

import lyfe.lyfeBe.user.domain.User
import java.time.Instant

data class PictureWhisky(
    val id: Long,
    val user : User,
    val topicPicture: TopicPicture,
    val createdAt: Instant?,
)

package lyfe.lyfeBe.picture

import lyfe.lyfeBe.user.User
import java.time.Instant

data class PictureWhisky(
    val id: Long,
    val user : User,
    val topicPicture: TopicPicture,
    val createdAt: Instant?,
)

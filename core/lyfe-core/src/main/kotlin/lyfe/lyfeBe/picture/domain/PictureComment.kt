package lyfe.lyfeBe.picture.domain

import lyfe.lyfeBe.user.domain.User
import java.time.Instant

data class PictureComment(
    val id: Long,
    val content: String,
    val depth: Int,
    val sequence: Int,
    val user: User,
    val topicPicture: TopicPicture,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val deletedAt: Instant?,
    val visibility: Boolean,
)

package lyfe.lyfeBe.picture.domain

import lyfe.lyfeBe.topic.domain.Topic
import lyfe.lyfeBe.user.domain.User
import java.time.Instant

data class TopicPicture(
    val id: Long,
    val picture: String,
    val title: String,
    val user: User,
    val topic: Topic,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val deletedAt: Instant?,
    val visibility: Boolean,
)

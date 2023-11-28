package lyfe.lyfeBe.picture

import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import java.time.Instant

data class TopicPicture(
    val id: Long,
    val picture: Image,
    val title: String,
    val user: User,
    val topic: Topic,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val deletedAt: Instant?,
    val visibility: Boolean,
)

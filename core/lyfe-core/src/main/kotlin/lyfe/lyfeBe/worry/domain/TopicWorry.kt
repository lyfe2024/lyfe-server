package lyfe.lyfeBe.worry.domain

import lyfe.lyfeBe.topic.domain.Topic
import lyfe.lyfeBe.user.domain.User
import java.time.Instant

data class TopicWorry(
    val id: Long,
    val title: String,
    val content: String,
    val user: User,
    val topic: Topic,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val deletedAt: Instant?,
    val visibility: Boolean,
)

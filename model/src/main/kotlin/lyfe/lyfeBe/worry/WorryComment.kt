package lyfe.lyfeBe.worry

import lyfe.lyfeBe.user.User
import java.time.Instant

data class WorryComment(
    val id: Long,
    val content: String,
    val depth: Int,
    val sequence: Int,
    val deletedAt: Instant?,
    val user: User,
    val topicWorry: TopicWorry,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val visibility: Boolean,
)

package lyfe.lyfeBe.worry

import lyfe.lyfeBe.user.User
import java.time.Instant

data class WorryWhisky(
    val id : Long,
    val user: User,
    val topicWorry: TopicWorry,
    val createdAt: Instant?,
)

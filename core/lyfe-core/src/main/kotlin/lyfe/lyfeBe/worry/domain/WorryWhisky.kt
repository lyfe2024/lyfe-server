package lyfe.lyfeBe.worry.domain

import lyfe.lyfeBe.user.domain.User
import java.time.Instant

data class WorryWhisky(
    val id : Long,
    val user: User,
    val topicWorry: TopicWorry,
    val createdAt: Instant?,
)

package lyfe.lyfeBe.feedback.domain

import lyfe.lyfeBe.user.domain.User
import java.time.Instant

data class Feedback(
    val id: Long,
    val content: String,
    val checked: Boolean,
    val createdAt: Instant,
    val user: User
)

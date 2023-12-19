package lyfe.lyfeBe.feedback

import lyfe.lyfeBe.user.User
import java.time.Instant

data class Feedback(
    val id: Long = 0,
    val content: String,
    val checked: Boolean = false,
    val createdAt: Instant? = null,
    val user: User
) {
    companion object {
        fun from(feedBack: String, user: User): Feedback {
            return Feedback(
                content = feedBack,
                user = user
            )
        }
    }
}

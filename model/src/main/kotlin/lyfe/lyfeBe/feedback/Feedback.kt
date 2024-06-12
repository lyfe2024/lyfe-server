package lyfe.lyfeBe.feedback

import lyfe.lyfeBe.user.User
import java.time.Instant

data class Feedback(
    val id: Long,
    val content: String,
    val checked: Boolean,
    val createdAt: Instant,
    val user: User
){
    fun check() =
        Feedback(
            id = id,
            content = content,
            checked = true,
            createdAt = createdAt,
            user = user
        )
    companion object {
        fun from(
            feedbackCreate: FeedbackCreate,
            user: User
        ): Feedback {
            return Feedback(
                id = 0,
                content = feedbackCreate.feedback,
                checked = false,
                createdAt = Instant.now(),
                user = user
            )
        }
    }
}

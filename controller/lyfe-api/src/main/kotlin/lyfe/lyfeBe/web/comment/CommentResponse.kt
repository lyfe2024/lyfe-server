package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.web.user.UserResponse
import java.time.Instant

data class CommentResponse(
    val id: Long,
    val content: String,
    val commentGroupId: Long?,
    val user : UserResponse,
    val createdAt: String
)

package lyfe.lyfeBe.web.comment

import java.time.Instant

data class CommentResponse(
    val id: Long,
    val content: String,
    val commentGroupId: Long?,
    val user : UserResponse,
    val boardId: Long,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val visibility: Boolean,
)

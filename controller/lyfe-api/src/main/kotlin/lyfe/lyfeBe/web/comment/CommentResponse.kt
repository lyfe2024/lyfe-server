package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.web.user.UserResponse

data class CommentResponse(
    val id: Long,
    val content: String,
    val user : UserResponse,
    val commentGroupId: Long?,
    val createdAt: String
)

package lyfe.lyfeBe.comment

data class CommentUpdate(
    val commentId: Long,
    val content: String,
    val userId: Long
)
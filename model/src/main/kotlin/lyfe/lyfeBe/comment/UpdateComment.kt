package lyfe.lyfeBe.comment

data class UpdateComment(
    val commentId: Long,
    val content: String,
    val userId: Long
)
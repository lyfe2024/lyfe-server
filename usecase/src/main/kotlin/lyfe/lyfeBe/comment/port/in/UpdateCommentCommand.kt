package lyfe.lyfeBe.comment.port.`in`

data class UpdateCommentCommand(
    val commentId: Long,
    val content: String,
    val userId: Long
)
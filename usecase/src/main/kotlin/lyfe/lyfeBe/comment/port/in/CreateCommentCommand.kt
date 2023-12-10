package lyfe.lyfeBe.comment.port.`in`

data class CreateCommentCommand(
    val content: String,
    val commentGroupId: Long?,
    val userId: Long,
    val boardId: Long
)
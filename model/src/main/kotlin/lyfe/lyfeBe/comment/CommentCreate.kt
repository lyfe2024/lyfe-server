package lyfe.lyfeBe.comment

data class CommentCreate(
    val content: String,
    val commentGroupId: Long?,
    val boardId: Long
)
package lyfe.lyfeBe.comment

data class CreateComment(
    val content: String,
    val commentGroupId: Long?,
    val userId: Long,
    val boardId: Long
)
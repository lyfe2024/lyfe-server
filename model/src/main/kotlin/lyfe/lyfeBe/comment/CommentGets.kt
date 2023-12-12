package lyfe.lyfeBe.comment


data class CommentGetsByBoard(
    val boardId: Long,
    val cursorId: Long,
)

data class CommentGetsByUserId(
    val userId: Long,
    val cursorId: Long,
)
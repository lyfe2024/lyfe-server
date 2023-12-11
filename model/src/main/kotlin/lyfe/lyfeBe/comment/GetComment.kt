package lyfe.lyfeBe.comment


data class GetCommentByBoard(
    val boardId: Long,
    val cursorId: Long,
)

data class GetCommentByUserId(
    val boardId: Long,
    val userId: Long,
    val cursorId: Long,
)
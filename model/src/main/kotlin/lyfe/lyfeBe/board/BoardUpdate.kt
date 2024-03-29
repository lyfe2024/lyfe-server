package lyfe.lyfeBe.board

data class BoardUpdate(
    val boardId: Long,
    val title: String,
    val content: String,
    val imageUrl: String? = null,
    val userId: Long
)

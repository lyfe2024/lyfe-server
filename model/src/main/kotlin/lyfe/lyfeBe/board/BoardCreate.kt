package lyfe.lyfeBe.board


data class BoardCreate(
        val title: String,
        val content: String,
        val boardType: BoardType,
        val userId: Long,
        val topicId: Long,
        val imageUrl: String? = null
)

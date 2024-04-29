package lyfe.lyfeBe.comment

import org.springframework.data.domain.Pageable


data class CommentGetsByBoard(
    val boardId: Long,
    val cursorId: Long?,
    val pageable: Pageable,
)
package lyfe.lyfeBe.comment.port.`in`

import org.springframework.data.domain.Pageable

data class GetCommentByBoardCommand(
    val boardId: Long,
    val cursorId: Long,
    val pageable: Pageable
)

data class GetCommentByUserIdCommand(
    val boardId: Long,
    val userId: Long,
    val cursorId: Long,
    val pageable: Pageable
)
package lyfe.lyfeBe.board

import org.springframework.data.domain.Pageable


data class BoardsUserGet(
    val cursorId: Long,
    val type: BoardType,
    val pageable: Pageable
)

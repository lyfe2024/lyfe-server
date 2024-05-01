package lyfe.lyfeBe.board

import org.springframework.data.domain.Pageable


data class BoardsPopularGet(
    val cursorId: Long,
    val popularType: PopularType,
    val type: BoardType,
    val pageable: Pageable
)

package lyfe.lyfeBe.board

import org.springframework.data.domain.Pageable


data class BoardsUserGet(val userId: Long, val cursorId: Long, val type: BoardType, val pageable: Pageable)

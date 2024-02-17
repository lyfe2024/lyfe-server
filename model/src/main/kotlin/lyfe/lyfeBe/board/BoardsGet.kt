package lyfe.lyfeBe.board

import org.springframework.data.domain.Pageable

data class BoardsGet(val boardId: Long, val date: String?, val pageable: Pageable, val type: BoardType)

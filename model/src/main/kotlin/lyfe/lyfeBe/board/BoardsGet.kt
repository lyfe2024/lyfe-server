package lyfe.lyfeBe.board

import org.springframework.data.domain.Pageable

data class BoardsGet(val boardId: Long , val size: Int)

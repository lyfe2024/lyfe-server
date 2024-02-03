package lyfe.lyfeBe.board

import org.springframework.data.domain.Pageable


data class BoardPicturesGet(val boardId: Long, val pageable: Pageable)

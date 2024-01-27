package lyfe.lyfeBe.board

import org.springframework.data.domain.Pageable


data class BoardsPopularGet(val boardId: Long , val whiskyCount :Long,val pageable: Pageable)

package lyfe.lyfeBe.board

import org.springframework.data.domain.Pageable

data class BoardsBestGet(
    val cursorId: Long,
    val pageable: Pageable
)

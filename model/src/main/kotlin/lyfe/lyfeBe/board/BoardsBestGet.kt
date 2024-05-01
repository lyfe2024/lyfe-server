package lyfe.lyfeBe.board

import org.springframework.data.domain.Pageable
import java.time.LocalDate

data class BoardsBestGet(
    val cursor: LocalDate,
    val pageable: Pageable
)

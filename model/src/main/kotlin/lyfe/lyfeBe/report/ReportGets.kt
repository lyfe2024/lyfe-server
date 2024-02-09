package lyfe.lyfeBe.report

import org.springframework.data.domain.Pageable

data class ReportGets(
    val cursorId: Long,
    val pageable: Pageable,
)

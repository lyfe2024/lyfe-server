package lyfe.lyfeBe.report

import org.springframework.data.domain.Pageable

data class ReportGets(
    val reportId: Long,
    val cursorId: Long,
    val pageable: Pageable,
)

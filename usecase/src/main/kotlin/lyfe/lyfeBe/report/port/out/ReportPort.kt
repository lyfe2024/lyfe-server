package lyfe.lyfeBe.report.port.out

import lyfe.lyfeBe.report.Report
import org.springframework.data.domain.Pageable

interface ReportPort {
    fun create(report: Report): Report
    fun getById(reportId: Long): Report
    fun getByUserIdAndReportTargetId(userId: Long, reportTargetId: Long): Report?
    fun getReportedCount(reportTargetId: Long): Int
    fun getReportsWithCursor(
        reportId: Long,
        cursorId: Long,
        pageable: Pageable
    ): List<Report>

    fun cancel(report: Report)

    fun getReportedCountsByUserIds(userIds: Set<Long>): Map<Long, Int>
}
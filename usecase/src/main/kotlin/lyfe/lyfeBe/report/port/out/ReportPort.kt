package lyfe.lyfeBe.report.port.out

import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.ReportTarget
import org.springframework.data.domain.Pageable

interface ReportPort {
    fun create(report: Report): Report
    fun update(report: Report): Report
    fun getById(reportId: Long): Report
    fun getByUserIdAndReportTargetIdAndReportTarget(userId: Long, reportTargetId: Long, reportTarget: ReportTarget): Report?
    fun getReportedCount(reportedUserId: Long): Int
    fun getReportsWithCursor(cursorId: Long, pageable: Pageable): List<Report>
    fun cancel(report: Report): Report
}
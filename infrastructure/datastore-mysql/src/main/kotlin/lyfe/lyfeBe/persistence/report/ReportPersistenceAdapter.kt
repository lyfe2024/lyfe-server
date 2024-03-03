package lyfe.lyfeBe.persistence.report

import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.ReportTarget
import lyfe.lyfeBe.report.port.out.ReportPort
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class ReportPersistenceAdapter(
    private val reportJpaRepository: ReportJpaRepository
) : ReportPort {

    @Transactional
    override fun create(report: Report): Report {
        check(report.id == 0L) { "ID가 0 or null이 아니면 생성할 수 없습니다." }
        return reportJpaRepository.save(ReportJpaEntity.from(report)).toDomain()
    }

    override fun update(report: Report): Report {
        return reportJpaRepository.save(ReportJpaEntity.from(report)).toDomain()
    }

    override fun getById(reportId: Long): Report {
        return reportJpaRepository.findById(reportId)
            .orElseThrow { throw IllegalArgumentException("해당하는 신고가 존재하지 않습니다.") }.toDomain()
    }

    override fun getByUserIdAndReportTargetIdAndReportTarget(userId: Long, reportTargetId: Long, reportTarget: ReportTarget): Report? {
        return reportJpaRepository.findByReporterIdAndReportTargetIdAndReportTarget(
            reporterId = userId,
            reportTargetId = reportTargetId,
            reportTarget = reportTarget)?.toDomain()
    }

    override fun getReportedCount(reportedUserId: Long): Int {
        return reportJpaRepository.findByReportedUserId(reportedUserId).size
    }

    override fun getReportsWithCursor(
        cursorId: Long,
        pageable: Pageable
    ): List<Report> {
        return reportJpaRepository.findReportsWithCursor(cursorId, pageable)
            .map { it.toDomain() }.toList()
    }

    override fun cancel(report: Report): Report {
        val from = ReportJpaEntity.from(report)
        from.cancelReport()
        val save = reportJpaRepository.save(from)
        return save.toDomain()
    }
}
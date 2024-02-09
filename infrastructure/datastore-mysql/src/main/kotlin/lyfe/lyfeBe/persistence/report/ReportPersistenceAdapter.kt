package lyfe.lyfeBe.persistence.report

import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.port.out.ReportPort
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class ReportPersistenceAdapter(
    private val reportJpaRepository: ReportJpaRepository
): ReportPort {
    @Transactional
    override fun create(report: Report): Report {
        check(report.id == 0L) { "ID가 0 or null이 아니면 생성할 수 없습니다." }
        return reportJpaRepository.save(ReportJpaEntity.from(report)).toDomain()
    }

    override fun getById(reportId: Long): Report {
        return reportJpaRepository.findById(reportId)
            .orElseThrow { throw IllegalArgumentException("해당하는 신고가 존재하지 않습니다.") }.toDomain()
    }

    override fun getByUserIdAndReportTargetId(userId: Long, reportTargetId: Long): Report? {
        return reportJpaRepository.findByReporterIdAndReportTargetId(userId, reportTargetId)?.toDomain()
    }

    override fun getReportedCount(reportTargetId: Long): Int {
        return reportJpaRepository.findByReportTargetId(reportTargetId).size
    }

    override fun getReportsWithCursor(
        reportId: Long,
        cursorId: Long,
        pageable: Pageable
    ): List<Report> {
        return reportJpaRepository.findAllByIdAndIdLessThanOrderByIdDesc(reportId, cursorId, pageable)
            .map { it.toDomain() }.toList()
    }

    override fun cancel(report: Report) {
        ReportJpaEntity.from(report).let {
            it.cancelReport()
            reportJpaRepository.save(it)
        }
    }

    override fun getReportedCountsByUserIds(userIds: Set<Long>): Map<Long, Int> {
        return reportJpaRepository.countReportsByTargetIds(userIds).map { it[0] as Long to it[1] as Int }.toMap()
    }

}
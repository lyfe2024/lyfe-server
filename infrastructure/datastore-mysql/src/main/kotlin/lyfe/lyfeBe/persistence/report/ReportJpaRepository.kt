package lyfe.lyfeBe.persistence.report

import lyfe.lyfeBe.report.ReportTarget
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ReportJpaRepository : JpaRepository<ReportJpaEntity, Long> {
    fun findByReporterIdAndReportTargetIdAndReportTarget(reporterId: Long, reportTargetId: Long, reportTarget: ReportTarget): ReportJpaEntity?
    @Query("SELECT r FROM ReportJpaEntity r WHERE r.reportedUser.id = :reportTargetId")
    fun findByReportedUserId(reportTargetId: Long): List<ReportJpaEntity>
    @Query("SELECT r FROM ReportJpaEntity r WHERE r.id < :cursorId ORDER BY r.id DESC")
    fun findReportsWithCursor(cursorId: Long, pageable: Pageable): List<ReportJpaEntity>

}
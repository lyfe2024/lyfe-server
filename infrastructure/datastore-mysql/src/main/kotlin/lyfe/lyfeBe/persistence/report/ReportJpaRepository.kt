package lyfe.lyfeBe.persistence.report

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ReportJpaRepository : JpaRepository<ReportJpaEntity, Long> {
    fun findByReporterIdAndReportTargetId(reporterId: Long, reportTargetId: Long): ReportJpaEntity?
    fun findByReportTargetId(reportTargetId: Long): List<ReportJpaEntity>
    @Query("SELECT r FROM ReportJpaEntity r WHERE r.id < :cursorId ORDER BY r.id DESC")
    fun findReportsWithCursor(cursorId: Long, pageable: Pageable): List<ReportJpaEntity>
    @Query("SELECT r.reportTargetId, COUNT(r) FROM ReportJpaEntity r WHERE r.reportTargetId IN :targetIds GROUP BY r.reportTargetId")
    fun countReportsByTargetIds(targetIds: Set<Long>): List<Array<Any>>

}
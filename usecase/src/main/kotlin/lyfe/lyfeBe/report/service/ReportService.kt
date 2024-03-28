package lyfe.lyfeBe.report.service

import lyfe.lyfeBe.auth.service.SecurityUtils
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.ReportCreate
import lyfe.lyfeBe.report.ReportGets
import lyfe.lyfeBe.report.ReportTarget
import lyfe.lyfeBe.report.dto.ReportDto
import lyfe.lyfeBe.report.dto.ReportListDto
import lyfe.lyfeBe.report.dto.SaveReportDto
import lyfe.lyfeBe.report.port.out.ReportPort
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ReportService(
    private val reportPort: ReportPort,
    private val userPort: UserPort,
    private val boardPort: BoardPort,
    private val commentPort: CommentPort
) {

    // 신고 생성
    @Transactional
    fun createReport(reportCreate: ReportCreate): SaveReportDto {
        val user = getLoginUser()
        val reportedUser = userPort.getById(reportCreate.reportTargetId)
        validateReportTargetExists(reportCreate.reportTarget, reportCreate.reportTargetId)
        val report = Report.from(reportCreate, user, reportedUser)
        checkDuplicatedReport(report)
        return SaveReportDto.from(reportPort.create(report))
    }

    // 신고 단건 조회
    fun getReportById(reportId: Long): ReportDto {
        val report = reportPort.getById(reportId)
        val reportedUser = userPort.getById(report.reportedUser.id)
        val reportedCount = reportPort.getReportedCount(reportedUser.id)
        return ReportDto.from(report, reportedUser, reportedCount)
    }

    // 신고 리스트 조회
    fun getReports(command: ReportGets): ReportListDto {
        val reports = reportPort.getReportsWithCursor(
            cursorId = command.cursorId,
            pageable = command.pageable
        )
        return ReportListDto.toListDto(
            reports.map {
                val reportedUser = userPort.getById(it.reportedUser.id)
                val reportedCount = reportPort.getReportedCount(it.reportTargetId)
                ReportDto.from(it, reportedUser, reportedCount)
            }
        )
    }

    // 신고 취소
    @Transactional
    fun cancelReport(reportId: Long): SaveReportDto {
        val report = reportPort.getById(reportId)
        checkAdminRole()
        val cancel = reportPort.cancel(report)
        return SaveReportDto.from(reportPort.update(cancel))
    }


    fun checkDuplicatedReport(report: Report) {
        val duplicatedReport = reportPort.getByUserIdAndReportTargetIdAndReportTarget(
            report.reporter.id,
            report.reportTargetId, report.reportTarget)
        require(duplicatedReport == null) { "이미 신고한 게시글입니다." }
    }

    fun checkAdminRole() {
        val user = getLoginUser()
        require(user.role == Role.ADMIN) { "관리자만 이 작업을 수행할 수 있습니다." }
    }

    fun getLoginUser(): User {
        return SecurityUtils.getLoginUser(userPort)
    }

    fun validateReportTargetExists(reportTarget: ReportTarget, reportTargetId: Long) {
        require(reportTargetId > 0L) { "신고 대상 ID가 유효하지 않습니다." }

        when (reportTarget) {
            ReportTarget.BOARD, ReportTarget.BOARD_PICTURE -> boardPort.getById(reportTargetId)
            ReportTarget.USER -> userPort.getById(reportTargetId)
            ReportTarget.COMMENT -> commentPort.getById(reportTargetId)
            else -> throw IllegalArgumentException("지원하지 않는 신고 대상 유형입니다.")
        }
    }
}
package lyfe.lyfeBe.report.service

import lyfe.lyfeBe.auth.service.SecurityUtils
import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.ReportCreate
import lyfe.lyfeBe.report.ReportGets
import lyfe.lyfeBe.report.dto.ReportDto
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
    private val userPort: UserPort
) {

    // 신고 생성
    @Transactional
    fun createReport(reportCreate: ReportCreate): SaveReportDto {
        val user = getLoginUser()
        val reportedUser = userPort.getById(reportCreate.reportTargetId)
        val report = Report.from(reportCreate, user, reportedUser)
        checkDuplicatedReport(report)
        return SaveReportDto.from(report)
    }

    // 신고 단건 조회
    fun getReportById(reportId: Long): ReportDto {
        val report = reportPort.getById(reportId)
        val reportedUser = userPort.getById(report.reportTargetId)
        val reportedCount = reportPort.getReportedCount(report.reportTargetId)
        return ReportDto.from(report, reportedUser, reportedCount)
    }

    // 신고 리스트 조회
    fun getReportsByUserId(command: ReportGets): List<ReportDto> {
        val reports = reportPort.getReportsWithCursor(
            reportId = command.reportId,
            cursorId = command.cursorId,
            pageable = command.pageable
        )

        // 신고된 사용자 ID 목록 생성
        val reportedUserIds = reports.map { it.reportedUser.id }.toSet()

        // 신고된 사용자별 신고 횟수 조회
        val reportedCounts = reportPort.getReportedCountsByUserIds(reportedUserIds)

        return reports.map { report ->
            // 각 신고된 사용자의 신고 횟수를 가져옴
            val reportedCount = reportedCounts[report.reportedUser.id] ?: 0
            ReportDto.from(report, report.reportedUser, reportedCount)
        }

    }

    // 신고 취소
    @Transactional
    fun cancelReport(reportId: Long): SaveReportDto {
        val report = reportPort.getById(reportId)
        checkAdminRole()
        reportPort.cancel(report)
        return SaveReportDto.from(report)
    }


    fun checkDuplicatedReport(report: Report) {
        val duplicatedReport = reportPort.getByUserIdAndReportTargetId(report.reporter.id, report.reportTargetId)
        require(duplicatedReport == null) { "이미 신고한 게시글입니다." }
    }

    fun checkAdminRole() {
        val user = getLoginUser()
        require(user.role == Role.ADMIN) { "관리자만 이 작업을 수행할 수 있습니다." }
    }


    fun getLoginUser(): User {
        return SecurityUtils.getLoginUser(userPort)
    }
}
package lyfe.lyfeBe.report.service

import lyfe.lyfeBe.auth.service.SecurityUtils
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.ReportCreate
import lyfe.lyfeBe.report.ReportGets
import lyfe.lyfeBe.report.ReportTarget
import lyfe.lyfeBe.report.dto.ReportDto
import lyfe.lyfeBe.report.dto.ReportListDto
import lyfe.lyfeBe.report.dto.ReportMessageDto
import lyfe.lyfeBe.report.dto.SaveReportDto
import lyfe.lyfeBe.report.port.out.ReportPort
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

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
        val reportedUserId = validateReportTargetExists(reportCreate.reportTarget, reportCreate.reportTargetId)
        val reportedUser = userPort.getById(reportedUserId)
        if (user.id == reportedUserId) {
            throw IllegalArgumentException("자신을 신고할 수 없습니다.")
        }

        val report = Report.from(reportCreate, user, reportedUser)
        checkDuplicatedReport(report)

        val saveReport = reportPort.create(report)
        updateUserStatus(reportedUser)

        return SaveReportDto.from(saveReport)
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

    // 특정 유저의 신고 현황 확인
    fun checkReportedStatus(): ResponseEntity<CommonResponse<ReportMessageDto>> {
        val user = getLoginUser()
        val userStatus = user.userStatus

        if (userStatus != UserStatus.ACTIVE && user.warningConsent != true) {
            val reportedCount = reportPort.getReportedCount(user.id)

            val messages = mapOf(
                5 to Pair("신고가 5회 누적되었습니다.", "신고가 5회 누적되었습니다.\n5일간 게시글과 댓글 작성이 불가하며 관련 게시글/댓글은 삭제됩니다."),
                15 to Pair("신고가 15회 누적되었습니다.", "신고가 15회 누적되었습니다.\n15일간 게시글과 댓글 작성이 불가하며 관련 게시글/댓글은 삭제됩니다."),
                30 to Pair(
                    "신고가 30회 누적되었습니다.",
                    "신고가 30회 누적되었습니다.\n30일간 게시글과 댓글 작성이 불가하며 관련 게시글/댓글은 삭제됩니다.\n50회 이상 누적 신고될 경우 계정 이용에 제한이 있을 수 있습니다."
                ),
                50 to Pair(
                    "계정이용이 정지되었습니다.",
                    "50회 이상 신고되어 계정이용이 정지되었습니다. 메일을 통해 문의 부탁 드립니다. Lyfe 대표 메일: sectionr0@gmail.com"
                )
            )

            val (title, content) = when {
                reportedCount >= 50 -> messages[50]
                reportedCount >= 30 -> messages[30]
                reportedCount >= 15 -> messages[15]
                reportedCount >= 5 -> messages[5]
                else -> null
            } ?: Pair("신고가 ${reportedCount}회 누적되었습니다.", "")
            val response = ReportMessageDto(title = title, content = content)
            return ResponseEntity.status(HttpStatus.OK).body(CommonResponse(response))
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }
    }

    @Transactional
    fun updateReportMessageConsent() {
        val user = getLoginUser()
        val updateUser = user.updateWarningConsent(true)
        userPort.update(updateUser)
    }

    // 신고 취소
    @Transactional
    fun cancelReport(reportId: Long): SaveReportDto {
        val report = reportPort.getById(reportId)
        checkAdminRole()
        val cancel = reportPort.cancel(report)
        return SaveReportDto.from(reportPort.update(cancel))
    }

    @Transactional
    fun updateUserStatus(user: User) {
        val reportCount = reportPort.getReportedCount(user.id)
        val warningAt = Instant.now()

        val updateUser: User? = when {
            reportCount >= 50 -> user.updateSuspended()
            reportCount == 30 -> user.updateWarning(warningAt.plusSeconds(60L * 60 * 24 * 30))
            reportCount == 15 -> user.updateWarning(warningAt.plusSeconds(60L * 60 * 24 * 15))
            reportCount == 5 -> user.updateWarning(warningAt.plusSeconds(60L * 60 * 24 * 5))
            else -> null
        }

        updateUser?.let { userPort.update(it) }
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

    fun validateReportTargetExists(reportTarget: ReportTarget, reportTargetId: Long): Long {
        require(reportTargetId > 0L) { "신고 대상 ID가 유효하지 않습니다." }

        val userId = when (reportTarget) {
            ReportTarget.BOARD, ReportTarget.BOARD_PICTURE -> boardPort.getById(reportTargetId).user.id
            ReportTarget.USER -> userPort.getById(reportTargetId).id
            ReportTarget.COMMENT -> commentPort.getById(reportTargetId).user.id
            else -> throw IllegalArgumentException("지원하지 않는 신고 대상 유형입니다.")
        }
        return userId
    }
}

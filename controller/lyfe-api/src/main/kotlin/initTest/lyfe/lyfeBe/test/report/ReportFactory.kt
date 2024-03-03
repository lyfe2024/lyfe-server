package initTest.lyfe.lyfeBe.test.report

import initTest.lyfe.lyfeBe.test.user.UserFactory
import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.ReportTarget
import lyfe.lyfeBe.user.User
import java.time.Instant

class ReportFactory {

    companion object {
        fun createTestReport(
            id: Long = 1L,
            reportTarget: ReportTarget = ReportTarget.BOARD,
            reportTargetId: Long = 1L,
            reporter: User = UserFactory.createTestUser(),
            reportedUser: User = UserFactory.createTestUser(2L),
            isCanceled: Boolean = false,
            canceledAt: Instant? = null,
            createdAt: Instant? = null,
            updatedAt: Instant? = null
        ): Report {
            return Report(
                id = id,
                reportTarget = reportTarget,
                reportTargetId = reportTargetId,
                reporter = reporter,
                reportedUser = reportedUser,
                isCanceled = isCanceled,
                canceledAt = canceledAt,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
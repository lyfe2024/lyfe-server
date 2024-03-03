package lyfe.lyfeBe.report

import lyfe.lyfeBe.user.User
import java.time.Instant

data class Report(
    val id: Long,
    val reportTarget: ReportTarget,
    val reportTargetId: Long,
    val reporter : User,
    val reportedUser : User,
    val isCanceled : Boolean,
    val canceledAt: Instant?,
    val createdAt: Instant?,
    val updatedAt: Instant?
){
    companion object {
        fun from(
            reportCreate: ReportCreate,
            reporter: User,
            reportedUser: User
        ): Report {
            return Report(
                id = 0,
                reportTarget = reportCreate.reportTarget,
                reportTargetId = reportCreate.reportTargetId,
                reporter = reporter,
                reportedUser = reportedUser,
                isCanceled = false,
                canceledAt = null,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }
    }
}

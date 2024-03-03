package lyfe.lyfeBe.report.dto

import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.user.User
import java.time.Instant

data class ReportDto(
    val id: Long,
    val reporterId: Long,
    val reporterEmail: String,
    val reporterNickname: String,
    val reportTargetId: Long,
    val reportTargetType: String,
    val reportedUserId: Long,
    val reportedUserEmail: String,
    val reportedUserNickname: String,
    val reportedUserCount: Int,
    val isCanceled: Boolean,
    val canceledAt: Instant?,
    val createdAt: Instant?,
    val updatedAt: Instant?,
){
    companion object {
        fun from(report : Report, reportedUser: User, reportedCount: Int): ReportDto {
            return ReportDto(
                id = report.id,
                reporterId = report.reporter.id,
                reporterEmail = report.reporter.email,
                reporterNickname = report.reporter.nickname,
                reportTargetId = report.reportTargetId,
                reportTargetType = report.reportTarget.name,
                reportedUserId = reportedUser.id,
                reportedUserEmail = reportedUser.email,
                reportedUserNickname = reportedUser.nickname,
                reportedUserCount = reportedCount,
                isCanceled = report.isCanceled,
                canceledAt = report.canceledAt,
                createdAt = report.createdAt,
                updatedAt = report.updatedAt
            )
        }
    }
}

package lyfe.lyfeBe.web.report.req

import lyfe.lyfeBe.report.ReportTarget

data class SaveReportRequest(
    val reportTarget: ReportTarget,
    val reportTargetId: Long
)

package lyfe.lyfeBe.web.report

import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.report.ReportCreate
import lyfe.lyfeBe.report.ReportGets
import lyfe.lyfeBe.report.dto.ReportDto
import lyfe.lyfeBe.report.dto.SaveReportDto
import lyfe.lyfeBe.report.service.ReportService
import lyfe.lyfeBe.web.report.req.SaveReportRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/reports")
class ReportController(
    private val service: ReportService
) {
    @PostMapping
    fun createReport(
        @RequestBody req: SaveReportRequest
    ): CommonResponse<SaveReportDto> {
        return service.createReport(
            ReportCreate(
                reportTarget = req.reportType,
                reportTargetId = req.reportTargetId
            )
        ).let { CommonResponse(it) }
    }

    @GetMapping("/{reportId}")
    fun getReport(
        @PathVariable reportId: Long
    ): CommonResponse<ReportDto> {
        return service.getReportById(reportId)
            .let { CommonResponse(it) }
    }

    @GetMapping
    fun getReports(
        @RequestParam(required = false) reportId: Long,
        @RequestParam(required = false) cursorId: Long,
        @PageableDefault(size = 10, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): CommonResponse<List<ReportDto>> {
        return service.getReportsByUserId(
            ReportGets(
                reportId = reportId,
                cursorId = cursorId,
                pageable = pageable
            )
        ).let { CommonResponse(it) }
    }

    @PostMapping("/cancel")
    fun cancelReport(
        @RequestBody req: SaveReportRequest
    ): CommonResponse<SaveReportDto> {
        return service.cancelReport(req.reportTargetId)
            .let { CommonResponse(it) }
    }

}
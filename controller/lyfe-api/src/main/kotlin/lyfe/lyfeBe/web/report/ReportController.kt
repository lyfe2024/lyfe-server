package lyfe.lyfeBe.web.report

import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.report.ReportCreate
import lyfe.lyfeBe.report.ReportGets
import lyfe.lyfeBe.report.dto.ReportDto
import lyfe.lyfeBe.report.dto.SaveReportDto
import lyfe.lyfeBe.report.service.ReportService
import lyfe.lyfeBe.utils.ControllerUtils
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
                reportTarget = req.reportTarget,
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
        @RequestParam(required = false) cursorId: Long,
        @PageableDefault(size = 10, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): CommonResponse<List<ReportDto>> {
        val reportId = ControllerUtils.getEffectiveCursorId(cursorId)
        return service.getReports(
            ReportGets(
                cursorId = reportId,
                pageable = pageable
            )
        ).let { CommonResponse(it) }
    }

    @PutMapping("/{reportId}/cancel")
    fun cancelReport(
        @PathVariable reportId: Long
    ): CommonResponse<SaveReportDto> {
        return service.cancelReport(reportId)
            .let { CommonResponse(it) }
    }

}
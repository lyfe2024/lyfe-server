package lyfe.lyfeBe.report.dto

import lyfe.lyfeBe.report.Report

data class SaveReportDto(
    val id : Long,
){
    companion object {
        fun from(report : Report): SaveReportDto {
            return SaveReportDto(
                id = report.id,
            )
        }
    }
}

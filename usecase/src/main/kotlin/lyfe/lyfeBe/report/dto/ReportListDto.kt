package lyfe.lyfeBe.report.dto

data class ReportListDto (
    val list : List<ReportDto>
){
    companion object {
        fun toListDto(list : List<ReportDto>) : ReportListDto {
            return ReportListDto(list)
        }
    }
}
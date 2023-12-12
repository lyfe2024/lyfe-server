package lyfe.lyfeBe.fomatter

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class dateConverter {

    companion object {
        fun toInstant(date: String): Instant {
            val localDate = LocalDate.parse(date) // LocalDate로 파싱
            val startOfDateTime = localDate.atStartOfDay(ZoneId.systemDefault()) // 자정 시간 설정
            val instant = startOfDateTime.toInstant() // Instant로 변환
            return instant
        }
    }
}
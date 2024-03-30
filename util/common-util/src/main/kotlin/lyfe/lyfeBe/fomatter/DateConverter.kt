package lyfe.lyfeBe.fomatter

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DateConverter {

    companion object {
        fun toInstant(date: String): Instant {
            val localDate = LocalDate.parse(date) // LocalDate로 파싱
            val startOfDateTime = localDate.atStartOfDay(ZoneId.systemDefault()) // 자정 시간 설정
            val instant = startOfDateTime.toInstant() // Instant로 변환
            return instant
        }

        fun parseDateToInstant(date: String): Instant {
            val formatter = DateTimeFormatter.ofPattern("yy-MM-dd")
            val localDate = LocalDate.parse(date, formatter)
            val startOfDateTime = localDate.atStartOfDay(ZoneId.systemDefault())
            return startOfDateTime.toInstant()
        }

        //Instant를 yy-MM-dd 포맷의 String으로 변환
        fun formatInstant(instant: Instant): String {
            val formatter = DateTimeFormatter.ofPattern("yy-MM-dd").withZone(ZoneId.systemDefault())
            return formatter.format(instant)
        }
    }
}
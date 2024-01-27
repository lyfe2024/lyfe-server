package lyfe.lyfeBe.fomatter

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class CursorGenerator {

    companion object {
        fun createCursorValue(id: Long, whiskyCount: Long): String {
            val pow10 = 10_000_000_000
            val paddedWhiskyCount = (pow10 - whiskyCount).toString().padStart(10, '0')
            val paddedId = (pow10 - id).toString().padStart(10, '0')

            return paddedWhiskyCount + paddedId
        }
    }
}
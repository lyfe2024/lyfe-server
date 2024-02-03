package lyfe.lyfeBe.fomatter

class CursorGenerator {

    companion object {
        fun createCursorValue(whiskyCount: Long): String {
            val pow10 = 10_000_000_000
            val fixedIdValue = "9999999999"

            val paddedWhiskyCount = (pow10 - whiskyCount).toString().padStart(10, '0')

            return paddedWhiskyCount + fixedIdValue
        }
    }


}
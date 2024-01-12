package lyfe.lyfeBe.utils

class ControllerUtils {
    companion object {
        fun getEffectiveCursorId(cursorId: Long?): Long {
            return cursorId?.takeIf { it != 0L } ?: Long.MAX_VALUE
        }
    }
}
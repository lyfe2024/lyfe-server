package lyfe.lyfeBe.whisky.dto

data class SaveWhiskyDto(
    val id: Long
) {
    companion object {
        fun from(whiskyId: Long): SaveWhiskyDto {
            return SaveWhiskyDto(
                id = whiskyId
            )
        }
    }
}
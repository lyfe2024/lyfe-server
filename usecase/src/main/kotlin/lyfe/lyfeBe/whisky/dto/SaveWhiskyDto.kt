package lyfe.lyfeBe.whisky.dto

data class SaveWhiskyDto(
    val whiskyId: Long
) {

    companion object {
        fun from(whiskyId: Long) = SaveWhiskyDto(whiskyId)

    }
}
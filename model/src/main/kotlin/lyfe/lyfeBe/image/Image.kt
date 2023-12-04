package lyfe.lyfeBe.image

data class Image(
        val id: Long,
        val url: String,
        val type: ImageType,
        val width: Int,
        val height: Int
) {

}
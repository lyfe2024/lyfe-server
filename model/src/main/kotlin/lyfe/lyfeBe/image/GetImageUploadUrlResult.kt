package lyfe.lyfeBe.image

import java.time.ZonedDateTime

data class GetImageUploadUrlResult(
    val url: String,
    val key: String,
    val expiresAt: ZonedDateTime
)

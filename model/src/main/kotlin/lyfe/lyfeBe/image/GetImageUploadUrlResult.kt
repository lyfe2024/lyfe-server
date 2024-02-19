package lyfe.lyfeBe.image

import java.net.URL
import java.time.LocalDateTime

data class GetImageUploadUrlResult(
    val url: URL,
    val key: String,
)

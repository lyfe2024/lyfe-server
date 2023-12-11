package lyfe.lyfeBe.topic

import java.time.Instant

data class Topic(
    val id: Long,
    val content: String,
    val appliedAt: Instant? = null,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val visibility: Boolean,
)

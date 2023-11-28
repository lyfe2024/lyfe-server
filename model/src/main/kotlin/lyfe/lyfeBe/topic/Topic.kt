package lyfe.lyfeBe.topic

import java.time.Instant

data class Topic(
    val id: Long,
    val content: String,
    val appliedAt: Instant?,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val visibility: Boolean,
)

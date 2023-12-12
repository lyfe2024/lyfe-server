package lyfe.lyfeBe.topic

import org.springframework.data.domain.Pageable

data class TopicPastGet(
    val date: String,
    val cursorId: Long,
    val pageable: Pageable
)

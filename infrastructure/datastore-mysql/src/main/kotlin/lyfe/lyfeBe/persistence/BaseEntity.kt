package lyfe.lyfeBe.persistence

import jakarta.persistence.Embeddable
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@Embeddable
class BaseEntity(
    @CreatedDate
    var createdAt: Instant? = null,
    @LastModifiedDate
    var updatedAt: Instant? = null,
    var visibility: Boolean = true
) {
    fun softDelete() {
        visibility = false
    }
}
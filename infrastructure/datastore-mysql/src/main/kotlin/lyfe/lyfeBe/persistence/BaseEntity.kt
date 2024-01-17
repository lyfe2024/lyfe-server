package lyfe.lyfeBe.persistence

import jakarta.persistence.Embeddable
import jakarta.persistence.EntityListeners
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Embeddable
@EntityListeners(AuditingEntityListener::class)
class BaseEntity(
    @CreatedDate
    var createdAt: Instant? = null,
    @LastModifiedDate
    var updatedAt: Instant? = null
) {

}
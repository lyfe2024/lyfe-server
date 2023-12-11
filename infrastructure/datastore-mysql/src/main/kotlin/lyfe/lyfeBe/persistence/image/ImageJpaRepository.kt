package lyfe.lyfeBe.persistence.image

import lyfe.lyfeBe.image.ImageType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ImageJpaRepository : JpaRepository<ImageJpaEntity, Long> {
    fun findByIdAndType(id: Long, type: ImageType): Optional<ImageJpaEntity>
    fun findByUserId(userId: Long): Optional<ImageJpaEntity>
    fun findByUserIdIn(userIds: List<Long>): List<ImageJpaEntity>
}
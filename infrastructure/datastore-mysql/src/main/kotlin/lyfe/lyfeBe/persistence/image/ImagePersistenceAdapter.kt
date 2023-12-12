package lyfe.lyfeBe.persistence.image

import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.image.ImageType
import lyfe.lyfeBe.image.port.out.ImagePort
import org.springframework.stereotype.Repository

@Repository
class ImagePersistenceAdapter(
    private val imageJpaRepository: ImageJpaRepository,
) : ImagePort {
    override fun create(image: Image): Image {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long) = imageJpaRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("image not found") }
            .toDomain() //

    override fun getByUserId(userId: Long) = imageJpaRepository.findByUserId(userId)
            .orElseThrow { ResourceNotFoundException("image not found") }
            .toDomain()

    override fun getByIdAndByType(id: Long, type: ImageType) = imageJpaRepository.findByIdAndType(id, type)
            .orElseThrow { ResourceNotFoundException("image not found") }
            .toDomain()

    override fun getByUserIds(userIds: List<Long>) = imageJpaRepository.findByUserIdIn(userIds)
            .map { it.toDomain() }
            .toList()
}
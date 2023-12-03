package lyfe.lyfeBe.persistence.image

import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.image.port.out.ImagePort
import org.springframework.stereotype.Repository

@Repository
class ImagePersistenceAdapter(
    private val imageJpaRepository: ImageJpaRepository,
) : ImagePort {
    override fun create(image: Image): Image {
        TODO("Not yet implemented")
    }

}
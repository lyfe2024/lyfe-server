package lyfe.lyfeBe.persistence.image

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.port.BoardRepository
import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.image.port.out.ImageRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ImagePersistenceAdapter(
    private val imageJpaRepository: ImageJpaRepository,
) : ImageRepository {
    override fun create(image: Image): Image {
        TODO("Not yet implemented")
    }

}
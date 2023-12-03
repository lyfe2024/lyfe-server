package lyfe.lyfeBe.image.port.out

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardCreate
import lyfe.lyfeBe.image.Image
import org.springframework.data.domain.PageRequest
import java.util.*

interface ImageRepository {
    fun create(image: Image): Image
}
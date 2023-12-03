package lyfe.lyfeBe.image.port.out

import lyfe.lyfeBe.image.Image

interface ImagePort {
    fun create(image: Image): Image
}
package lyfe.lyfeBe.image.port.out

import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.image.ImageType

interface ImagePort {
    fun create(image: Image): Image
    fun update(image: Image): Image
    fun getById(id: Long): Image
    fun getByUserId(userId: Long): Image?
    fun getByIdAndByType(id: Long , type: ImageType): Image
    fun getByUserIds(userIds: List<Long>): List<Image>
}
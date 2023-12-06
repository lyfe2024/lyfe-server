package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.image.ImageType
import lyfe.lyfeBe.image.port.out.ImagePort
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class FakeImageRepository : ImagePort {
    private val autoGeneratedId = AtomicLong(0)
    private val data: MutableList<Image> = Collections.synchronizedList(ArrayList())


    override fun create(image: Image): Image {
        val newImage = image.copy(id = autoGeneratedId.incrementAndGet())
        data.add(newImage)
        return newImage
    }

    override fun getById(id: Long) = data.find { it.id == id }!!

    override fun getByUserId(userId: Long) = data.find { it.user!!.id == userId }!!

    override fun getByIdAndByType(id: Long, type: ImageType): Image {
        TODO("Not yet implemented")
    }

    override fun getByUserIds(userIds: List<Long>): List<Image> {
        TODO("Not yet implemented")
    }
}
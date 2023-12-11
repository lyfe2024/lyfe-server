package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.port.out.UserPort
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class FakeUserRepository : UserPort {
    private val autoGeneratedId = AtomicLong(0)
    private val data: MutableList<User> = Collections.synchronizedList(ArrayList())

    override fun create(user: User): User {
        return if (user.id == 0L) {
            val newUser = user.copy(id = autoGeneratedId.incrementAndGet())
            data.add(newUser)
            newUser
        } else {
            data.removeIf { it.id == user.id }
            data.add(user)
            user
        }
    }

    override fun findById(userId: Long) = data.find { it.id == userId }

    override fun getByIdAndValidateActive(id: Long): User {
        TODO("Not yet implemented")
    }

    override fun getById(userId: Long): User {
        return data.find { it.id == userId }!!
    }

    fun clear() {
        data.clear()
    }
}

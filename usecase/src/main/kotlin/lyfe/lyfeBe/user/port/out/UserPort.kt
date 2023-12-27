package lyfe.lyfeBe.user.port.out

import lyfe.lyfeBe.user.User

interface UserPort {
    fun create(user: User): User
    fun findById(userId: Long): User?
    fun getByIdAndValidateActive(id: Long): User
    fun getById(userId: Long): User
    fun getByEmail(email: String): User?
    fun findByEmail(email: String): User?
}
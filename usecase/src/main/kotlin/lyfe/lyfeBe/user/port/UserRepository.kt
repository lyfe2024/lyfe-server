package lyfe.lyfeBe.user.port

import lyfe.lyfeBe.user.User

interface UserRepository {
    fun findById(userId: Long): User?
    fun getByIdAndValidateActive(id: Long): User
    fun getById(userId: Long) : User


}
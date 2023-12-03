package lyfe.lyfeBe.user.port.out

import lyfe.lyfeBe.user.User

interface UserPort {
    fun findById(userId: Long): User?
    fun getByIdAndValidateActive(id: Long): User
    fun getById(userId: Long) : User
}
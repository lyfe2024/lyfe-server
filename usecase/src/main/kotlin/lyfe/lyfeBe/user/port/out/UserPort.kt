package lyfe.lyfeBe.user.port.out

import lyfe.lyfeBe.user.User

interface UserPort {
    fun create(user: User): User
    fun findById(userId: Long): User?
    fun getById(userId: Long): User
    fun getByEmail(email: String): User?
    fun update(user: User): User
    fun existsByNickname(nickname: String): Boolean
}
package lyfe.lyfeBe.auth.port.out

import lyfe.lyfeBe.auth.RefreshToken
import lyfe.lyfeBe.user.User

interface RefreshTokenPort {

    fun create(refreshToken: RefreshToken): RefreshToken
    fun findByUser(user: User): RefreshToken?
    fun saveOrUpdate(refreshToken: RefreshToken): RefreshToken
    fun deleteByUserId(userId: Long)
    fun findByRefreshToken(refreshToken: String): RefreshToken?
}
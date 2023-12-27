package lyfe.lyfeBe.auth.port.out

import lyfe.lyfeBe.auth.RefreshToken

interface RefreshTokenPort {

    fun create(refreshToken: RefreshToken): RefreshToken
}
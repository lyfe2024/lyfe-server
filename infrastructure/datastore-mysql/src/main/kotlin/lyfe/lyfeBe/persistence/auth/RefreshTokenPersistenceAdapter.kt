package lyfe.lyfeBe.persistence.auth

import lyfe.lyfeBe.auth.RefreshToken
import lyfe.lyfeBe.auth.port.out.RefreshTokenPort
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
class RefreshTokenPersistenceAdapter(
    private val refreshTokenRepository: RefreshTokenJpaRepository
) : RefreshTokenPort {
    override fun create(refreshToken: RefreshToken): RefreshToken {
        return refreshTokenRepository.save(RefreshTokenJpaEntity.from(refreshToken)).toDomain()
    }

}
package lyfe.lyfeBe.persistence.auth

import lyfe.lyfeBe.auth.RefreshToken
import lyfe.lyfeBe.auth.port.out.RefreshTokenPort
import lyfe.lyfeBe.user.User
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

    override fun findByUser(user: User): RefreshToken? {
        return refreshTokenRepository.findByUserId(user.id)?.toDomain()
    }

    override fun saveOrUpdate(refreshToken: RefreshToken): RefreshToken {
        val refreshTokenJpaEntity = refreshTokenRepository.findByUserId(refreshToken.user.id)
        return if (refreshTokenJpaEntity != null) {
            refreshTokenJpaEntity.update(refreshToken.refreshToken)
            refreshTokenRepository.save(refreshTokenJpaEntity).toDomain()
        } else {
            create(refreshToken)
        }
    }

    override fun deleteByUserId(userId: Long) {
        refreshTokenRepository.deleteByUserId(userId)
    }

}
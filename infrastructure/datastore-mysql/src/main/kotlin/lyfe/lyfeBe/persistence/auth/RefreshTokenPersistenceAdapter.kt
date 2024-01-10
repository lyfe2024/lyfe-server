package lyfe.lyfeBe.persistence.auth

import lyfe.lyfeBe.auth.RefreshToken
import lyfe.lyfeBe.auth.port.out.RefreshTokenPort
import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.user.User
import org.springframework.data.repository.findByIdOrNull
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
        return refreshTokenRepository.findByIdOrNull(user.id)?.toDomain()
            ?: throw ResourceNotFoundException("유저가 존재하지 않습니다.")
    }

    override fun update(refreshToken: RefreshToken): RefreshToken {
        val update = RefreshTokenJpaEntity.update(refreshToken)
        return refreshTokenRepository.save(update).toDomain()
    }

}
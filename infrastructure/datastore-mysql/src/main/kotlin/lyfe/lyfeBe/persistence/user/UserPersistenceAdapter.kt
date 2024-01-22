package lyfe.lyfeBe.persistence.user

import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
class UserPersistenceAdapter(
    private val userRepository: UserJpaRepository
) : UserPort {

    override fun getById(userId: Long): User {
        return userRepository.findById(userId).map(UserJpaEntity::toDomain)
            .orElseThrow { ResourceNotFoundException("user not found") }

    }

    override fun getByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.toDomain()
    }

    override fun update(user: User): User {
        return userRepository.save(UserJpaEntity.update(user)).toDomain()
    }

    override fun existsByNickname(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }

    override fun create(user: User) =
        userRepository.save(UserJpaEntity.from(user)).toDomain()

    override fun findById(userId: Long) =
        userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("user not found") }
            .toDomain()
}
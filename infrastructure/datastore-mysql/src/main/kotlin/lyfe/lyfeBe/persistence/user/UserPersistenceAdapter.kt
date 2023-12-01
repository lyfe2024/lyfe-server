package lyfe.lyfeBe.persistence.user

import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.port.UserRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
class UserPersistenceAdapter(
    private val userRepository: UserJpaRepository
) : UserRepository {


    override fun getByIdAndValidateActive(id: Long) =
        userRepository.findById(id)
            .orElseThrow { RuntimeException("user not found") }
            .toDomain() //FIXME N+1문제는 어떻게 해결할까? 이부분얘기필요

    override fun getById(userId: Long): User {
        return userRepository.findById(userId).map(UserJpaEntity::toDomain)
            .orElseThrow { RuntimeException("user not found") }

    }

    override fun findById(userId: Long) =
        userRepository.findById(userId)
            .orElseThrow { RuntimeException("user not found") }
            .toDomain()
}
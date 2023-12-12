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


    override fun getByIdAndValidateActive(id: Long) =
        userRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("user not found") }
            .toDomain() //FIXME N+1문제는 어떻게 해결할까? 이부분얘기필요

    override fun getById(userId: Long): User {
        return userRepository.findById(userId).map(UserJpaEntity::toDomain)
            .orElseThrow { ResourceNotFoundException("user not found") }

    }

    override fun create(user: User)=
            userRepository.save(UserJpaEntity.from(user)).toDomain()

    override fun findById(userId: Long) =
        userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("user not found") }
            .toDomain()
}
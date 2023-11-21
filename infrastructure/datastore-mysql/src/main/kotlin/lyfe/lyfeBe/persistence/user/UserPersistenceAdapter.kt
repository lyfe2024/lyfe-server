package lyfe.lyfeBe.persistence.user

import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.user.application.port.out.GetUserPort
import lyfe.lyfeBe.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository
) : GetUserPort {

    override fun getById(id: Long): User =
        userRepository.findByIdOrNull(id)
            ?.let { UserMapper.mapToDomainEntity(it) }
            ?: throw ResourceNotFoundException("해당하는 유저가 존재하지 않습니다.")

    override fun getByIdAndValidateActive(id: Long): User =
        getById(id = id).apply {
            validateActive()
        }

}
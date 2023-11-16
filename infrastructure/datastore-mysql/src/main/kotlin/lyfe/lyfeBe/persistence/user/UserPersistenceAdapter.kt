package lyfe.lyfeBe.persistence.user

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class UserPersistenceAdapter(
    private val userRepository: lyfe.lyfeBe.persistence.user.UserRepository
) {
}
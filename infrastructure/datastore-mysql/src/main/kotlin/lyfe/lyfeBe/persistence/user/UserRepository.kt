package lyfe.lyfeBe.persistence.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserJpaEntity, Long> {
    
}
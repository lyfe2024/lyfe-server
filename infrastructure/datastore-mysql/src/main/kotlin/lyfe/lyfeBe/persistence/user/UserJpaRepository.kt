package lyfe.lyfeBe.persistence.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository: JpaRepository<UserJpaEntity, Long> {
    fun findByEmail(email: String): UserJpaEntity?
    fun existsByNickname(nickname: String): Boolean
}
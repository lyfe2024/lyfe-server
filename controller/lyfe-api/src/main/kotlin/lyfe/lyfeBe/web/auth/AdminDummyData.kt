package lyfe.lyfeBe.web.auth

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import lyfe.lyfeBe.persistence.user.UserJpaRepository
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.UserStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AdminDummyData(
    private val userJpaRepository: UserJpaRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    private val log = KotlinLogging.logger { }

    @Transactional
    @PostConstruct
    fun dummyData() {
        if (userJpaRepository.count() > 0) {
            log.info("[0] 어드민, 유저가 이미 존재하여 더미를 생성하지 않았습니다.")
            return
        }

        val adminUser = UserJpaEntity(
            email = "12341234@GOOGLE",
            hashedPassword = passwordEncoder.encode("12341234"),
            nickname = "admin",
            socialType = SocialType.GOOGLE,
            socialId = "12341234",
            notificationConsent = false,
            fcmRegistration = false,
            userStatus = UserStatus.ACTIVE,
            role = Role.ADMIN,
            baseEntity = BaseEntity()
        )
        userJpaRepository.save(adminUser)

    }
}
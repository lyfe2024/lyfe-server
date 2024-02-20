package initTest.lyfe.lyfeBe.test.user

import lyfe.lyfeBe.auth.PrincipalDetails
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

class UserFactory {
    companion object {
        fun createTestUser(
            id: Long = 1L,
            name: String = "testName",
            email: String = "testEmail",
            password: String = "testPassword",
            role: Role = Role.USER,
            profileUrl: String = "https://example.com/image.jpg",
            status: UserStatus = UserStatus.ACTIVE
        ): User {
            return User(
                id = id,
                nickname = name,
                email = email,
                hashedPassword = password,
                socialId = "",
                socialType = SocialType.GOOGLE,
                socialRefreshToken = "@@@@@",
                notificationConsent = true,
                fcmRegistration = true,
                role = role,
                profileUrl = profileUrl,
                userStatus = status,
                )
        }

        fun setSecurityContextUser(user : User){
            // PrincipalDetails의 가짜 구현 생성
            val fakeUserDetails = PrincipalDetails(user)

            // TestingAuthenticationToken에 fakeUserDetails 설정
            val authentication = TestingAuthenticationToken(fakeUserDetails, null)
            SecurityContextHolder.getContext().authentication = authentication
        }
    }
}
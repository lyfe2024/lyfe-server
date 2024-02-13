package initTest.lyfe.lyfeBe.test.user

import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus

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
    }
}
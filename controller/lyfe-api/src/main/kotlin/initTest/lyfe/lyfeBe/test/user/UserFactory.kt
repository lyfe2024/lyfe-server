package initTest.lyfe.lyfeBe.test.user

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
            isAdmin: Boolean = true,
            isActive: Boolean = true,
            role: Role = Role.USER,
            profileUrl: String = "https://example.com/image.jpg",
            status: UserStatus = UserStatus.ACTIVE
        ): User {
            return User(
                id,
                name,
                email,
                password,
                isAdmin,
                isActive,
                role,
                profileUrl,
                status
            )
        }
    }
}
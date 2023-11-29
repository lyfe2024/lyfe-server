package lyfe.lyfeBe

import io.mockk.mockk
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.UserStatus

object MockObject {
    fun testUser(nickname: String): User = User(
        id = 0,
        email = "test@test.com",
        hashedPassword = "test",
        nickname = nickname,
        notificationConsent = false,
        fcmRegistration = false,
        profileImage = null,
        role = Role.USER,
        userStatus = UserStatus.ACTIVE,
        createdAt = null,
        updatedAt = null,
        withdrawnAt = null,
        visibility = true,
    )

    fun testComment(commentGroupId: Long? = null): Comment = Comment(
        id = 0,
        content = "test content",
        commentGroupId = commentGroupId,
        user = testUser(nickname = "test"),
        board = mockk(),
        createdAt = null,
        updatedAt = null,
        deletedAt = null,
        visibility = true
    )
}

package initTest.lyfe.lyfeBe.test.fcm

import lyfe.lyfeBe.notification.*
import lyfe.lyfeBe.user.User
import org.springframework.data.domain.Pageable
import java.time.Instant

class NotificationFactory {

    companion object {
        fun createNotificationSend() = NotificationSend(
            1L,
            "testToken",
            NotificationType.BOARD,
            NotificationContent.BOARDCOMMENTED
        )

        fun createNotificationHistory(user: User) = NotificationHistory(
            id = 1L,
            content = NotificationContent.BOARDCOMMENTED.description,
            notificationType = NotificationType.BOARD,
            user = user,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
        )

        fun createNotificationHistory(cursorId: Long, pageable: Pageable) = NotificationGet(
            notificationId = cursorId,
            pageable = pageable
        )

    }
}
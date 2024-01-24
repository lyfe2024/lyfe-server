package initTest.lyfe.lyfeBe.test.fcm.domain

import initTest.lyfe.lyfeBe.test.fcm.NotificationFactory.Companion.createNotificationSend
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardCreate
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.BoardUpdate
import lyfe.lyfeBe.notification.NotificationContent
import lyfe.lyfeBe.notification.NotificationHistory
import lyfe.lyfeBe.notification.NotificationSend
import lyfe.lyfeBe.notification.NotificationType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus


class NotificationTest(
) : BehaviorSpec({

    Given("NotificationSend, User 객체가 초기화되었을 때") {

        val notificationSend = createNotificationSend()

        val user = createTestUser()

        When("BoardCreate, User, Topic 객체를 사용하여 새 Board 객체를 생성했을 때") {
           val notificationHistory = NotificationHistory.from(notificationSend, user)

            Then("생성된 Board 객체의 속성이 boardCreate와 일치") {
                notificationHistory.user.id shouldBe 1L
                notificationHistory.content shouldBe notificationSend.content.description
                notificationHistory.notificationType shouldBe notificationSend.type
            }
        }
    }
})
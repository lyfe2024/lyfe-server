package initTest.lyfe.lyfeBe.test.fcm.domain

import initTest.lyfe.lyfeBe.test.fcm.NotificationFactory.Companion.createNotificationSend
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.notification.NotificationHistory



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
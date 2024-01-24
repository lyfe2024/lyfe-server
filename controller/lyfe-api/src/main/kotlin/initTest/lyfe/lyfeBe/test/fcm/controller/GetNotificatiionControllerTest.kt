package initTest.lyfe.lyfeBe.test.fcm.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.notification.NotificationContent
import lyfe.lyfeBe.notification.NotificationHistory
import lyfe.lyfeBe.notification.NotificationType
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.Instant


class GetNotificatiionControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()

    beforeContainer {

        val user = User(
            id = 1L,
            email = "testUser@example.com",
            hashedPassword = "hashedPassword",
            nickname = "testUser",
            notificationConsent = true,
            fcmRegistration = true,
            role = Role.USER,
            profileUrl = "https://example.com/image.jpg",

            userStatus = UserStatus.ACTIVE
        )
        testContainer.userRepository.create(user)

        val notificationHistory = NotificationHistory(
            id = 1L,
            content = NotificationContent.BOARDCOMMENTED.description,
            notificationType = NotificationType.BOARD,
            user = user,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
        )
        testContainer.fakeNotificationRepository.save(notificationHistory)
    }


    Given("알림 목록 조회를 위한 요청 데이터가 준비되었을 때") {


        val of = PageRequest.of(
            0, // 페이지 번호 (0부터 시작)
            5, // 페이지 크기
            Sort.by("id").descending()
        )


        When("알림목록을 조회 했을 때") {
            val res = testContainer.notificationController.getNotificationHistories(1L, of)

            Then("저장된 알림 목록의 필드 와 응답값 과 일치해야 한다") {
                res.forEach { notificationHistory ->
                    notificationHistory.content shouldBe NotificationContent.BOARDCOMMENTED.description
                    notificationHistory.notificationType shouldBe NotificationType.BOARD
                    notificationHistory.user.id shouldBe 1L
                }
            }

        }
    }

})
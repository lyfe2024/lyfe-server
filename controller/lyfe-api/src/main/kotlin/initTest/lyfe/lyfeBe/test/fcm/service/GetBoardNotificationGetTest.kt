package initTest.lyfe.lyfeBe.test.fcm.service

import initTest.lyfe.lyfeBe.test.fcm.NotificationFactory.Companion.createNotificationHistory
import initTest.lyfe.lyfeBe.test.mock.FakeNotificationRepository
import initTest.lyfe.lyfeBe.test.mock.FakeUserRepository
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.fcm.FCMService
import lyfe.lyfeBe.notification.NotificationContent
import lyfe.lyfeBe.notification.NotificationType
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort


class GetBoardNotificationGetTest(
) : BehaviorSpec({

    val fakeNotificationRepository = FakeNotificationRepository()
    val fakeUserRepository = FakeUserRepository()
    val fCMService = FCMService(
        fakeNotificationRepository,
        fakeUserRepository
    )

    beforeContainer {

        val user = createTestUser()
        fakeUserRepository.create(user)

        println(" NotificationContent.BOARDCOMMENTED.description")
        println(NotificationContent.BOARDCOMMENTED.description)

        val notificationHistory = createNotificationHistory(user)


        fakeNotificationRepository.save(notificationHistory)
    }






    Given("알림 알림 히스토리 데이터가 준비됬을 때 ") {

        When("알림 목록을 조회할 때") {
            val cursorId = Long.MAX_VALUE
            val pageable = PageRequest.of(
                0, // 페이지 번호 (0부터 시작)
                5, // 페이지 크기
                Sort.by("id").descending()
            )

            val notificationGet = createNotificationHistory(cursorId, pageable)


            val notificationDtos = fCMService.getNotificationHistories(notificationGet)

            Then("조회된 알림 목록이 초기 설정과 일치하는지 확인할 때") {
                notificationDtos.forEach { notiDto ->
                    notiDto.content shouldBe NotificationContent.BOARDCOMMENTED.description
                    notiDto.notificationType shouldBe NotificationType.BOARD
                    notiDto.user.id shouldBe 1L
                }
            }
        }
    }
})
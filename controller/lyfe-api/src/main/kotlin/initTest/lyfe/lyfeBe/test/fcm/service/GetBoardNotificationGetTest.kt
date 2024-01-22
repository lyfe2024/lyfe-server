package initTest.lyfe.lyfeBe.test.fcm.service

import initTest.lyfe.lyfeBe.test.mock.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.BoardGet
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.BoardsGet
import lyfe.lyfeBe.fcm.FCMService
import lyfe.lyfeBe.notification.NotificationContent
import lyfe.lyfeBe.notification.NotificationGet
import lyfe.lyfeBe.notification.NotificationHistory
import lyfe.lyfeBe.notification.NotificationType
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.Instant


class GetBoardNotificationGetTest(
) : BehaviorSpec({

    val fakeNotificationRepository = FakeNotificationRepository()
    val fakeUserRepository = FakeUserRepository()
    val fCMService = FCMService(
        fakeNotificationRepository,
        fakeUserRepository
    )

    beforeContainer {

        val user = User(
            id = 1L,
            email = "testUser@example.com",
            hashedPassword = "hashedPassword",
            nickname = "testUser",
            notificationConsent = true,
            fcmRegistration = true,
            role = Role.USER,
            socialId = "testSocialId",
            socialType = lyfe.lyfeBe.auth.SocialType.GOOGLE,
            userStatus = UserStatus.ACTIVE
        )
        fakeUserRepository.create(user)

        println(" NotificationContent.BOARDCOMMENTED.description")
        println( NotificationContent.BOARDCOMMENTED.description)

        val notificationHistory = NotificationHistory(
            id = 1L,
            content = NotificationContent.BOARDCOMMENTED.description,
            notificationType = NotificationType.BOARD,
            user = user,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
        )
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

            val notificationGet = NotificationGet(
                notificationId = cursorId,
                pageable = pageable

            )
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
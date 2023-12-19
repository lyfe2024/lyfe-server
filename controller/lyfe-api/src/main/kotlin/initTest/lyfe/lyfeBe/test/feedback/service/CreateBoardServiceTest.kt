package initTest.lyfe.lyfeBe.test.feedback.service

import initTest.lyfe.lyfeBe.test.mock.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.feedback.FeedBackCreate
import lyfe.lyfeBe.feedback.FeedBackService
import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.image.ImageType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus


class CreateBoardServiceTest(
) : BehaviorSpec({

    val feedBackRepository = FakeFeedBackRepository()
    val fakeUserRepository = FakeUserRepository()
    val feedBackService = FeedBackService(
        feedBackRepository,
        fakeUserRepository,
    )

    beforeContainer {
        // 테스트에 필요한 사용자, 토픽, 게시물을 미리 생성하고 저장
        val user = User(
            id = 0,
            email = "testUser@example.com",
            hashedPassword = "hashedPassword",
            nickname = "testUser",
            notificationConsent = true,
            fcmRegistration = true,
            role = Role.USER,
            userStatus = UserStatus.ACTIVE,
            visibility = true
        )
        fakeUserRepository.create(user)


    }

    Given("게시판 생성 요청과 사용자, 주제가 준비되었을 때") {

        val feedBackCreate = FeedBackCreate(
            1L,
            "testFeedBack",
        )


        When("게시판 생성 요청을 처리할 때") {

            val feedBackId = feedBackService.create(feedBackCreate)

            val newFeedBack = feedBackRepository.getById(feedBackId)

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                newFeedBack.content shouldBe feedBackCreate.feedBack
            }
        }
    }
})
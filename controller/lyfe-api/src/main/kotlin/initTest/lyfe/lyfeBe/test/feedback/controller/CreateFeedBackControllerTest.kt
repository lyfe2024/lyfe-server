package initTest.lyfe.lyfeBe.test.feedback.controller

import initTest.lyfe.lyfeBe.test.mock.FakeFeedBackRepository
import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.web.feedback.CreateFeedbackRequest


class CreateFeedBackControllerTest(
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
            userStatus = UserStatus.ACTIVE,
            visibility = true
        )
        testContainer.userRepository.create(user)
    }



    Given("게시판 생성 요청이 준비되었을 때") {

        val req = CreateFeedbackRequest(
            userId = 1L,
            feedback = "testFeedback",
        )


        When("생성된 게시판의 제목, 내용, 유형이 요청과 일치해야 한다") {

            val feedBackDto = testContainer.feedbackController.create(req)

            val feedBack = testContainer.feedBackRepository.getById(feedBackDto.result.id)

            feedBack.content shouldBe req.feedback
            feedBack.user.id shouldBe req.userId
        }
    }
})
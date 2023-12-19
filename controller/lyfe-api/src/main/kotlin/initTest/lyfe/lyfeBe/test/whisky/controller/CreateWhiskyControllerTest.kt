package initTest.lyfe.lyfeBe.test.whisky.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.web.board.req.BoardSaveRequest
import lyfe.lyfeBe.web.whisky.req.WhiskySaveRequest


class CreateWhiskyControllerTest(
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

        val topic = Topic(1L, "testTopic", visibility = true)
        testContainer.topicRepository.create(topic)


        val req = BoardSaveRequest(
            title = "테스트 게시판 제목",
            content = "테스트 내용입니다. 여기에 게시판 내용이 들어갑니다.",
            boardType = BoardType.BOARD,
            userId = 1L,
            topicId = 1L
        )

        testContainer.boardController.create(req)
    }



    Given("Whisky 요청이 준비되었을 때") {

        val request = WhiskySaveRequest(
            userId = 1L,
            boardId = 1L
        )

        When("Whisky 생성 요청을 처리할 때") {
            val newWhisky = testContainer.whiskyController.create(request)
            val getWhisky = testContainer.whiskyRepository.get(newWhisky.result.whiskyId)
            Then("생성된 Whisky의 속성이 요청과 일치하는지 확인할 때") {
                getWhisky.user.id shouldBe request.userId
                getWhisky.board.id shouldBe request.boardId
            }
        }


    }
})
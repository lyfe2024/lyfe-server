package initTest.lyfe.lyfeBe.test.whisky.controller

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createBoardsSaveRequest
import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.topic.TopicFactory.Companion.createTesteTopic
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import initTest.lyfe.lyfeBe.test.whisky.WhiskyFactory.Companion.createWhiskySaveRequest
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

        val user = createTestUser()

        testContainer.userRepository.create(user)

        val topic = createTesteTopic()

        testContainer.topicRepository.create(topic)


        val req = createBoardsSaveRequest()

        testContainer.boardController.create(req)
    }



    Given("Whisky 요청이 준비되었을 때") {

        val request = createWhiskySaveRequest()

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
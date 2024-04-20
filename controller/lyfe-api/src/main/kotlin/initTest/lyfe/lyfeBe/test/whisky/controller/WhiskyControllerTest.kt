package initTest.lyfe.lyfeBe.test.whisky.controller

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createBoardsSaveRequest
import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.topic.TopicFactory.Companion.createTesteTopic
import initTest.lyfe.lyfeBe.test.user.UserFactory
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.http.HttpStatus

class WhiskyControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()

    beforeContainer {

        val user = createTestUser()
        testContainer.userRepository.create(user)

        val topic = createTesteTopic()
        testContainer.topicRepository.create(topic)

        UserFactory.setSecurityContextUser(user)

        val req = createBoardsSaveRequest()
        testContainer.boardController.create(req)
    }

    Given("Whisky 요청이 준비되었을 때") {

        When("Whisky 생성 요청을 처리할 때") {

            val saveDto = testContainer.whiskyController.likeWhiskyByBoardId(1L)

            Then("생성된 Whisky의 속성이 요청과 일치하는지 확인할 때") {
                saveDto.statusCode shouldBe HttpStatus.OK
            }
        }

        When("Whisky 삭제 요청을 처리할 때"){
            val saveDto = testContainer.whiskyController.likeWhiskyByBoardId(1L)

            Then("생성된 Whisky의 속성이 요청과 일치하는지 확인할 때") {
                saveDto.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }


    }
})
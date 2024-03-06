package initTest.lyfe.lyfeBe.test.whisky.controller

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createBoardsSaveRequest
import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.topic.TopicFactory.Companion.createTesteTopic
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import initTest.lyfe.lyfeBe.test.whisky.WhiskyFactory
import initTest.lyfe.lyfeBe.test.whisky.WhiskyFactory.Companion.createWhiskySaveRequest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
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

        When("좋아요가 없는 상태에서 좋아요를 추가") {
            testContainer.whiskyController.create(request)

            val addedWhisky = testContainer.whiskyRepository.assertNoExistingWhisky(request.boardId, request.userId)

            Then("좋아요가 성공적으로 추가됨") {
                addedWhisky.shouldNotBeNull()
            }
        }

        When("좋아요가 있는 상태에서 다시 토글하여 좋아요 해제") {

            // 기존 좋아요가 이미 있으므로 해제하기위해
            testContainer.whiskyController.create(request)

            val removedWhisky = testContainer.whiskyRepository.assertNoExistingWhisky(request.boardId, request.userId)

            Then("좋아요가 성공적으로 해제됨") {
                removedWhisky.shouldBeNull()
            }
        }
    }
})
package initTest.lyfe.lyfeBe.test.topic.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.user.UserFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.web.topic.req.SaveTopicRequest
import lyfe.lyfeBe.web.topic.req.UpdateTopicRequest
import java.time.LocalDate


class UpdateControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()

    lateinit var user : User

    beforeContainer {
        user = UserFactory.createTestAdmin()
        testContainer.userRepository.create(user)

        UserFactory.setSecurityContextUser(user)
    }

    Given("토픽을 데이터를 준비 할 때") {

        val req = SaveTopicRequest(
            content = "testTopic",
            appliedAt = null
        )
        testContainer.topicController.create(req).result


        When("토픽 업데이트 요청을 처리할 하고 조회 했을때") {

            val updateTopicRequest = UpdateTopicRequest(
                content = "testsetestsetestestes",
                appliedAt = LocalDate.now()
            )

            testContainer.topicController.update(1L, updateTopicRequest)
            val getTopicDto = testContainer.topicController.get(1L).result

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                getTopicDto.id shouldBe 1L
                getTopicDto.content shouldBe updateTopicRequest.content
            }
        }

    }
})
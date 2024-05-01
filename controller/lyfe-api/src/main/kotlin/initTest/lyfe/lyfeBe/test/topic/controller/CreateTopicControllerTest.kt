package initTest.lyfe.lyfeBe.test.topic.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.user.UserFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.web.topic.req.SaveTopicRequest


class CreateTopicControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()

    lateinit var user : User

    beforeContainer {
        user = UserFactory.createTestAdmin()
        testContainer.userRepository.create(user)

        UserFactory.setSecurityContextUser(user)
    }

    Given("토픽 생성 요청이 준비되었을 때") {

        val req = SaveTopicRequest(
            content = "testTopic",
            appliedAt = null
        )

        When("토픽 생성 요청을 처리할 하고 조회 했을때") {

            val saveDto = testContainer.topicController.create(req).result

            val getTopic = testContainer.topicController.get(saveDto.id)

            Then("생성된 토픽의 속성이 요청과 일치하는지 확인할 때") {
                getTopic.result.content shouldBe req.content
            }
        }

    }
})
package initTest.lyfe.lyfeBe.test.topic.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.web.topic.req.SaveTopicRequest


class CreateControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()

    Given("토픽 생성 요청이 준비되었을 때") {

        val req = SaveTopicRequest(
            content = "testTopic",
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
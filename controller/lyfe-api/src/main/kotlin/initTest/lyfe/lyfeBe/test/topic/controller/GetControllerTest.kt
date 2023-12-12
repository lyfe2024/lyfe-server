package initTest.lyfe.lyfeBe.test.topic.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.web.topic.req.SaveTopicRequest
import lyfe.lyfeBe.web.topic.req.UpdateTopicRequest


class GetControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()

    Given("토픽을 데이터를 준비 할 때") {

        val req = SaveTopicRequest(
            content = "testTopic",
        )
        testContainer.createTopicController.create(req).result


        When("저장된 토픽을 조회 했을때") {


            val getTopicDto = testContainer.getTopicController.get(1L).result

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                getTopicDto.id shouldBe 1L
                getTopicDto.content shouldBe req.content
            }
        }

    }
})
package initTest.lyfe.lyfeBe.test.topic.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.web.topic.req.SaveTopicRequest
import lyfe.lyfeBe.web.topic.req.UpdateTopicRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort


class GetControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()

    Given("토픽을 데이터를 준비 할 때") {

        val req = SaveTopicRequest(
            content = "testTopic",
        )
        testContainer.topicController.create(req).result


        When("저장된 토픽을 조회 했을때") {


            val getTopicDto = testContainer.topicController.get(1L).result

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                getTopicDto.id shouldBe 1L
                getTopicDto.content shouldBe req.content
            }
        }

    }

    Given("토픽을 데이터를 준비 할 때") {

        val req = SaveTopicRequest(
            content = "testTopic",
        )
        testContainer.topicController.create(req).result
        testContainer.topicController.create(req).result


        When("저장된 토픽을 조회 했을때") {

            val of = PageRequest.of(
                0, // 페이지 번호 (0부터 시작)
                5, // 페이지 크기
                Sort.by("id").descending()
            )

            val result = testContainer.topicController.getPastTopic("9999-12-31", Long.MAX_VALUE, of).result




            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                result.forEach{
                    it.content shouldBe req.content
                }
            }
        }

    }
})
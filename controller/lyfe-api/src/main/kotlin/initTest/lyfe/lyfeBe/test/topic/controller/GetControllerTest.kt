package initTest.lyfe.lyfeBe.test.topic.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.web.topic.req.SaveTopicRequest
import lyfe.lyfeBe.web.topic.req.UpdateTopicRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class GetControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()



    afterContainer {
        testContainer.topicRepository.clear()
    }


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


    Given("오늘의 Topic이 존재할 때") {
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))
        val req = SaveTopicRequest(
            content = "Today's Topic",
        )
        testContainer.topicController.create(req).result

        When("오늘의 Topic을 조회하면") {
            val todayTopic = testContainer.topicController.get().result

            Then("오늘 날짜의 Topic이 반환되어야 함") {
                todayTopic.content shouldBe "Today's Topic"
                todayTopic.appliedAt shouldBe today
            }
        }
    }

    Given("오늘의 Topic이 존재하지 않을 때") {
        When("오늘의 Topic을 조회하면") {
            Then("IllegalStateException이 발생해야 함") {
                shouldThrow<IllegalStateException> {
                    testContainer.topicController.get()
                }
            }
        }
    }
})
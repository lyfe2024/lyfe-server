package initTest.lyfe.lyfeBe.test.topic.service

import initTest.lyfe.lyfeBe.test.mock.FakeTopicRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.topic.TopicCreate
import lyfe.lyfeBe.topic.TopicGet
import lyfe.lyfeBe.topic.TopicPastGet
import lyfe.lyfeBe.topic.port.TopicService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort


class GetTopicServiceTest(
) : BehaviorSpec({

    val fakeTopicRepository = FakeTopicRepository()
    val topicService = TopicService(
        fakeTopicRepository
    )


    Given("Topic data가 준비되어있고 ") {


        val topicCreate = TopicCreate(
            content = "testTopic",
        )
        topicService.create(topicCreate)

        When("토픽 아이디로 조회 요청을 처리할 때") {

            val topicGet = TopicGet(
                1L
            )

            val topicDto = topicService.get(topicGet)

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                topicDto.id shouldBe 1L
                topicDto.content shouldBe "testTopic"
            }
        }
    }

    Given("Topic data가 준비되어있고 ") {


        val topicCreate = TopicCreate(
            content = "testTopic",
        )
        topicService.create(topicCreate)
        topicService.create(topicCreate)
        topicService.create(topicCreate)
        topicService.create(topicCreate)
        topicService.create(topicCreate)

        When("토픽 날짜로 과거 조회 요청을 처리할 때") {

            val topicpastGet = TopicPastGet(
                "9999-12-31",
                Long.MAX_VALUE,
                PageRequest.of(
                    0, // 페이지 번호 (0부터 시작)
                    5, // 페이지 크기
                    Sort.by("id").descending()
                )
            )
            val past = topicService.getPast(topicpastGet)


            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                past.size shouldBeLessThan 5
                past.forEach {
                    it.content shouldBe topicCreate.content
                }
            }
        }
    }
})
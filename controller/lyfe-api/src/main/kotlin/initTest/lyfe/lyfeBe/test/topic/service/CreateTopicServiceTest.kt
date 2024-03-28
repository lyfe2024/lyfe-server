package initTest.lyfe.lyfeBe.test.topic.service

import initTest.lyfe.lyfeBe.test.mock.FakeTopicRepository
import initTest.lyfe.lyfeBe.test.topic.TopicFactory.Companion.createTopicCreate
import initTest.lyfe.lyfeBe.test.topic.TopicFactory.Companion.createTopicUpdate
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.topic.TopicGet
import lyfe.lyfeBe.topic.port.TopicService
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class CreateTopicServiceTest(
) : BehaviorSpec({

    val fakeTopicRepository = FakeTopicRepository()
    val topicService = TopicService(
        fakeTopicRepository
    )

    Given("TopicCreate가  준비되었을 때") {

        val topicCreate = createTopicCreate("testTopic")


        When("토픽 업데이트를 처리 할고 조회 했을때 ") {

            val newTopic = topicService.create(topicCreate)
            val createTopic = topicService.get(TopicGet(newTopic.id))

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))

                createTopic.content shouldBe topicCreate.content
                createTopic.appliedAt shouldBe today

            }
        }
    }

    Given("Topic data가 준비되어있고 ") {


        val topicCreate = createTopicCreate("testTopic")

        topicService.create(topicCreate)

        When("토픽 업데이트 요청을 처리할 때") {

            val topicUpdate = createTopicUpdate(
                1L,
                "testTopic"
            )
            topicService.update(topicUpdate)
            val updateTopic = topicService.get(TopicGet(1L))

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                updateTopic.id shouldBe topicUpdate.topicId
                updateTopic.content shouldBe topicUpdate.content
            }
        }
    }
})
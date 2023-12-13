package initTest.lyfe.lyfeBe.test.topic.service

import initTest.lyfe.lyfeBe.test.mock.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.topic.TopicCreate
import lyfe.lyfeBe.topic.TopicGet
import lyfe.lyfeBe.topic.TopicUpdate
import lyfe.lyfeBe.topic.port.TopicService


class UpdateTopicServiceTest(
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

        When("토픽 업데이트 요청을 처리할 때") {

            val topicUpdate = TopicUpdate(
                1L,
                "testTopic",
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
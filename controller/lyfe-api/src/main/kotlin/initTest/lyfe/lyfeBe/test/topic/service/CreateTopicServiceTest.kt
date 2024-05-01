package initTest.lyfe.lyfeBe.test.topic.service

import initTest.lyfe.lyfeBe.test.mock.FakeTopicRepository
import initTest.lyfe.lyfeBe.test.mock.FakeUserRepository
import initTest.lyfe.lyfeBe.test.topic.TopicFactory.Companion.createTopicCreate
import initTest.lyfe.lyfeBe.test.topic.TopicFactory.Companion.createTopicUpdate
import initTest.lyfe.lyfeBe.test.user.UserFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.topic.TopicGet
import lyfe.lyfeBe.topic.port.TopicService
import lyfe.lyfeBe.user.User
import java.time.LocalDate


class CreateTopicServiceTest(
) : BehaviorSpec({

    val fakeTopicRepository = FakeTopicRepository()
    val fakeUserRepository = FakeUserRepository()
    val topicService = TopicService(
        topicPort = fakeTopicRepository,
        userPort = fakeUserRepository
    )

    lateinit var user: User

    beforeContainer {
        // 테스트에 필요한 사용자, 토픽, 게시물을 미리 생성하고 저장
        user = UserFactory.createTestAdmin()
        fakeUserRepository.create(user)

        UserFactory.setSecurityContextUser(user)
    }

    afterContainer {
        fakeUserRepository.clear()
        fakeTopicRepository.clear()
    }


    Given("TopicCreate가  준비되었을 때") {

        val topicCreate = createTopicCreate("testTopic")


        When("토픽 업데이트를 처리 할고 조회 했을때 ") {

            val newTopic = topicService.create(topicCreate)
            val createTopic = topicService.get(TopicGet(newTopic.id))

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                val today = LocalDate.now()

                createTopic.content shouldBe topicCreate.content
                createTopic.date shouldBe today

            }
        }
    }

    Given("Topic data가 준비되어있고 ") {

        val topicCreate = createTopicCreate("testTopic")

        val create = topicService.create(topicCreate)

        When("토픽 업데이트 요청을 처리할 때") {

            val topicUpdate = createTopicUpdate(
                create.id,
                "testTopic"
            )
            topicService.update(topicUpdate)
            val updateTopic = topicService.get(TopicGet(create.id))

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                updateTopic.id shouldBe topicUpdate.topicId
                updateTopic.content shouldBe topicUpdate.content
            }
        }
    }
})
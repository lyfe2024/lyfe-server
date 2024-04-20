package initTest.lyfe.lyfeBe.test.topic.service

import initTest.lyfe.lyfeBe.test.mock.FakeTopicRepository
import initTest.lyfe.lyfeBe.test.mock.FakeUserRepository
import initTest.lyfe.lyfeBe.test.user.UserFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.topic.TopicCreate
import lyfe.lyfeBe.topic.TopicGet
import lyfe.lyfeBe.topic.TopicUpdate
import lyfe.lyfeBe.topic.port.TopicService
import lyfe.lyfeBe.user.User
import java.time.LocalDate


class UpdateTopicServiceTest(
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


    Given("Topic data가 준비되어있고 ") {


        val topicCreate = TopicCreate(
            content = "testTopic",
            appliedAt = null
        )
        val saveTopicDto = topicService.create(topicCreate)

        When("토픽 업데이트 요청을 처리할 때") {

            val topicUpdate = TopicUpdate(
                topicId = saveTopicDto.id,
                content = "testTopic",
                appliedAt = LocalDate.now()
            )
            topicService.update(topicUpdate)
            val updateTopic = topicService.get(TopicGet(1L))

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                updateTopic.id shouldBe topicUpdate.topicId
                updateTopic.content shouldBe topicUpdate.content
                updateTopic.date shouldBe topicUpdate.appliedAt
            }
        }
    }
})
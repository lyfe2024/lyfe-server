package initTest.lyfe.lyfeBe.test.topic.service

import initTest.lyfe.lyfeBe.test.mock.FakeTopicRepository
import initTest.lyfe.lyfeBe.test.mock.FakeUserRepository
import initTest.lyfe.lyfeBe.test.user.UserFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.topic.TopicCreate
import lyfe.lyfeBe.topic.TopicGet
import lyfe.lyfeBe.topic.TopicPastGet
import lyfe.lyfeBe.topic.port.TopicService
import lyfe.lyfeBe.user.User
import java.time.LocalDate


class GetTopicServiceTest(
) : BehaviorSpec({

    val fakeTopicRepository = FakeTopicRepository()
    val fakeUserRepository = FakeUserRepository()
    val topicService = TopicService(
        fakeTopicRepository,
        fakeUserRepository,
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
            content = "testTopic333",
            appliedAt = null
        )

        val savedTopic = topicService.create(topicCreate)

        When("토픽 아이디로 조회 요청을 처리할 때") {

            val topicGet = TopicGet(
                savedTopic.id
            )

            val topicDto = topicService.get(topicGet)

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                topicDto.id shouldBe savedTopic.id
                topicDto.content shouldBe topicCreate.content
            }
        }
    }

    Given("Topic 과거 date를 조회할 때 ") {


        val topicCreate = TopicCreate(
            content = "testTopic",
            appliedAt = LocalDate.now().minusDays(1)
        )
        topicService.create(topicCreate)


        When("토픽 날짜로 과거 조회 요청을 처리할 때") {

            val topicpastGet = TopicPastGet(
                date = LocalDate.now().minusDays(1)
            )
            val past = topicService.getPast(topicpastGet)


            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                past.content shouldBe topicCreate.content
            }
        }
    }

    Given("오늘의 Topic이 존재할 때") {
        val today = LocalDate.now()
        val topicCreate = TopicCreate(
            content = "Today's Topic",
            appliedAt = today
        )
        topicService.create(topicCreate)

        When("오늘의 Topic을 조회하면") {
            val todayTopic = topicService.getToday()

            Then("오늘 날짜의 Topic이 반환되어야 함") {
                todayTopic.content shouldBe "Today's Topic"
                todayTopic.date shouldBe today
            }
        }
    }

    Given("오늘의 Topic이 존재하지 않을 때") {
        When("오늘의 Topic을 조회하면") {
            Then("ResourceNotFoundException 발생해야 함") {
                shouldThrow<ResourceNotFoundException> {
                    topicService.getToday()
                }
            }
        }
    }
})
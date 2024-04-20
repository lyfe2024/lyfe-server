package initTest.lyfe.lyfeBe.test.topic.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.user.UserFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.web.topic.req.SaveTopicRequest
import java.time.LocalDate


class GetTopicControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()
    lateinit var user : User

    beforeContainer {
        user = UserFactory.createTestAdmin()
        testContainer.userRepository.create(user)

        UserFactory.setSecurityContextUser(user)
    }

    afterContainer {
        testContainer.topicRepository.clear()
    }


    Given("토픽을 데이터를 준비 할 때") {

        val req = SaveTopicRequest(
            content = "testTopic",
            appliedAt = LocalDate.now()
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


    Given("오늘의 Topic이 존재할 때") {
        val today = LocalDate.now()
        val req = SaveTopicRequest(
            content = "Today's Topic",
            appliedAt = LocalDate.now()
        )
        testContainer.topicController.create(req).result

        When("오늘의 Topic을 조회하면") {
            val todayTopic = testContainer.topicController.getTodayTopic().result

            Then("오늘 날짜의 Topic이 반환되어야 함") {
                todayTopic.content shouldBe "Today's Topic"
                todayTopic.date shouldBe today
            }
        }
    }

    Given("오늘의 Topic이 존재하지 않을 때") {

        testContainer.topicRepository.clear()

        When("오늘의 Topic을 조회하면") {
            Then("ResourceNotFoundException이 발생해야 함") {
                shouldThrow<ResourceNotFoundException> {
                    testContainer.topicController.getTodayTopic()
                }
            }
        }
    }
})
package initTest.lyfe.lyfeBe.test.topic.service

import initTest.lyfe.lyfeBe.test.mock.FakeTopicRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.topic.TopicCreate
import lyfe.lyfeBe.topic.TopicGet
import lyfe.lyfeBe.topic.TopicPastGet
import lyfe.lyfeBe.topic.port.TopicService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class GetTopicServiceTest(
) : BehaviorSpec({

    val fakeTopicRepository = FakeTopicRepository()
    val topicService = TopicService(
        fakeTopicRepository
    )


    afterContainer {
        fakeTopicRepository.clear()
    }

    Given("Topic data가 준비되어있고 ") {


        val topicCreate = TopicCreate(
            content = "testTopic333",
        )

        val savedTopic = topicService.create(topicCreate)

        When("토픽 아이디로 조회 요청을 처리할 때") {

            val topicGet = TopicGet(
                savedTopic.id
            )
            println("@@")

            println(fakeTopicRepository.get())

            val topicDto = topicService.get(topicGet)

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                topicDto.id shouldBe savedTopic.id
                topicDto.content shouldBe topicCreate.content
            }
        }
    }

    Given("Topic data(여러개)가 준비되있고 ") {


        val topicCreate = TopicCreate(
            content = "testTopic",
        )
        topicService.create(topicCreate)
        topicService.create(topicCreate)
        topicService.create(topicCreate)
        topicService.create(topicCreate)
        topicService.create(topicCreate)

        When("토픽 날짜로 과거 조회 요청을 처리할 때") {

            val pageable = PageRequest.of(
                0, // 페이지 번호 (0부터 시작)
                5, // 페이지 크기
                Sort.by("id").descending()
            )

            val topicpastGet = TopicPastGet(
                "9999-12-31",
                pageable
            )
            val past = topicService.getPast(topicpastGet)


            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                past.size shouldBeLessThan 6
                past.forEach {
                    it.content shouldBe topicCreate.content
                }
            }
        }
    }

    Given("오늘의 Topic이 존재할 때") {
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))
        val topicCreate = TopicCreate(
            content = "Today's Topic",
        )
        topicService.create(topicCreate)

        When("오늘의 Topic을 조회하면") {
            val todayTopic = topicService.getToday()

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
                    topicService.getToday()
                }
            }
        }
    }
})
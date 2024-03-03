package initTest.lyfe.lyfeBe.test.report.controller

import initTest.lyfe.lyfeBe.test.board.BoardFactory
import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.report.ReportFactory
import initTest.lyfe.lyfeBe.test.user.UserFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.topic.Topic
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.SecurityContextHolder

class GetReportControllerTest: BehaviorSpec({

    val testContainer = TestContainer.build()
    lateinit var board: Board
    lateinit var topic: Topic
    lateinit var report: Report

    beforeContainer {

        val reporter = UserFactory.createTestUser()
        val reportedUser = UserFactory.createTestUser(2, "testUser2")
        testContainer.userRepository.create(reporter)
        testContainer.userRepository.create(reportedUser)

        board = BoardFactory.createTestBoard()
        testContainer.boardRepository.create(board)

        topic = Topic(1L, "testTopic")
        testContainer.topicRepository.create(topic)

        UserFactory.setSecurityContextUser(reporter)
    }

    afterContainer {
        // 테스트 후 SecurityContext 클리어
        SecurityContextHolder.clearContext()
    }

    Given("신고 조회 요청이 준비되었을 때") {

        report = ReportFactory.createTestReport()
        testContainer.reportRepository.create(report)

        When("신고를 조회 했을 때") {
            val res = testContainer.reportController.getReport(1L).result

            Then("저장된 신고의 필드와 응답값과 일치해야 한다.") {
                res.reportTargetId shouldBe 1L
                res.reportedUserNickname shouldBe "testUser2"
            }
        }
    }

    Given("신고 리스트 조회를 위한 데이터가 준비되엇을 때"){

        val pageable = PageRequest.of(
            0, // 페이지 번호 (0부터 시작)
            5, // 페이지 크기
            Sort.by("id").descending()
        )

        When("신고 리스트를 조회 했을 때") {
            val res = testContainer.reportController.getReports(0, pageable).result

            Then("저장된 신고의 필드와 응답값과 일치해야 한다.") {
                res.size shouldBe 1
                res[0].reportTargetId shouldBe 1L
                res[0].reportedUserNickname shouldBe "testUser2"
            }
        }
    }
})
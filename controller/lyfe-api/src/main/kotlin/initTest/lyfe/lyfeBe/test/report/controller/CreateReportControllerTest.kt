package initTest.lyfe.lyfeBe.test.report.controller

import initTest.lyfe.lyfeBe.test.board.BoardFactory
import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.user.UserFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.report.ReportTarget
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.web.report.req.SaveReportRequest
import org.springframework.security.core.context.SecurityContextHolder

class CreateReportControllerTest : BehaviorSpec({

    val testContainer = TestContainer.build()
    lateinit var board: Board
    lateinit var topic: Topic

    beforeContainer {
        val reporter = UserFactory.createTestUser()
        val reportedUser = UserFactory.createTestUser(2)
        testContainer.userRepository.create(reporter)
        testContainer.userRepository.create(reportedUser)

        board = BoardFactory.createTestBoard()
        testContainer.boardRepository.create(board)

        topic = Topic(1L, "testTopic")
        testContainer.topicRepository.create(topic)

        UserFactory.setSecurityContextUser(reporter)
    }

    afterContainer {
        SecurityContextHolder.clearContext()
    }

    Given(" 신고 생성 요청이 준비 되었을 때") {

        val req = SaveReportRequest(
            reportTarget = ReportTarget.BOARD,
            reportTargetId = 1L
        )

        When("신고 생성 요청을 처리할 하고 조회 했을때") {


            val reportId = testContainer.reportController.createReport(req)
            val report = testContainer.reportService.getReportById(reportId.result.id)


            Then("생성된 신고의 속성이 요청과 일치 하는지 확인할 때") {
                report.reportTargetType shouldBe req.reportTarget.toString()
                report.reportTargetId shouldBe req.reportTargetId
            }
        }

    }
})
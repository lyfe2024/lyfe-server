package initTest.lyfe.lyfeBe.test.report.service

import initTest.lyfe.lyfeBe.test.board.BoardFactory
import initTest.lyfe.lyfeBe.test.mock.*
import initTest.lyfe.lyfeBe.test.report.ReportFactory
import initTest.lyfe.lyfeBe.test.user.UserFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.ReportGets
import lyfe.lyfeBe.report.service.ReportService
import lyfe.lyfeBe.topic.Topic
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.SecurityContextHolder

class GetReportServiceTest: BehaviorSpec({

    val fakeReportRepository = FakeReportRepository()
    val fakeUserRepository = FakeUserRepository()
    val fakeBoardRepository = FakeBoardRepository()
    val fakeCommentRepository = FakeCommentRepository()
    val fakeTopicRepository = FakeTopicRepository()
    val reportService = ReportService(
        reportPort = fakeReportRepository,
        userPort = fakeUserRepository,
        boardPort = fakeBoardRepository,
        commentPort = fakeCommentRepository
    )
    lateinit var report: Report

    beforeContainer {

        val reporter = UserFactory.createTestUser()
        val reportedUser = UserFactory.createTestUser(2, "testUser2")

        fakeUserRepository.create(reporter)
        fakeUserRepository.create(reportedUser)

        val board = BoardFactory.createTestBoard()
        fakeBoardRepository.create(board)

        val topic = fakeTopicRepository.create(Topic(1L, "testTopic"))
        fakeTopicRepository.create(topic)

        UserFactory.setSecurityContextUser(reporter)
    }

    afterContainer {
        // 테스트 후 SecurityContext 클리어
        SecurityContextHolder.clearContext()
    }

    Given("신고 조회 요청이 준비되었을 때") {

        report = ReportFactory.createTestReport()
        fakeReportRepository.create(report)

        When("신고 조회 요청을 하면") {
            val reportDto = reportService.getReportById(1L)

            Then("신고 정보가 반환된다") {
                reportDto.id shouldBe 1L
                reportDto.reporterId shouldBe 1L
                reportDto.reportedUserId shouldBe 2L
                reportDto.reportTargetId shouldBe 1L
                reportDto.isCanceled shouldBe false
                reportDto.canceledAt shouldBe null
            }
        }
    }

    Given("신고 리스트 조회 요청이 준비 되었을 때"){

        val pageable = PageRequest.of(
            0, // 페이지 번호 (0부터 시작)
            5, // 페이지 크기
            Sort.by("id").descending()
        )

        val reportGets = ReportGets(
            cursorId = 0L,
            pageable = pageable
        )

        When("신고 리스트 조회 요청을 하면"){
            val reportList = reportService.getReports(reportGets)

            Then("신고 리스트가 반환된다"){
                reportList.list.size shouldBe 1
            }
        }
    }

})
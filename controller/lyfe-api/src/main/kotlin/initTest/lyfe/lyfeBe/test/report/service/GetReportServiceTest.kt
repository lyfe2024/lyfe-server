package initTest.lyfe.lyfeBe.test.report.service

import initTest.lyfe.lyfeBe.test.mock.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.auth.PrincipalDetails
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.ReportGets
import lyfe.lyfeBe.report.ReportTarget
import lyfe.lyfeBe.report.service.ReportService
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.Instant

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

    beforeContainer {

        val reporter = User(
            id = 1L,
            email = "testUser@example.com",
            hashedPassword = "hashedPassword",
            nickname = "testUser",
            notificationConsent = true,
            fcmRegistration = true,
            role = Role.USER,
            socialId = "testSocialId",
            socialType = SocialType.GOOGLE,
            userStatus = UserStatus.ACTIVE
        )

        val reportedUser = User(
            id = 2L,
            email = "testUser2@example.com",
            hashedPassword = "hashedPassword",
            nickname = "testUser2",
            notificationConsent = true,
            fcmRegistration = true,
            role = Role.USER,
            socialId = "testSocialId",
            socialType = SocialType.GOOGLE,
            userStatus = UserStatus.ACTIVE
        )


        fakeUserRepository.create(reporter)
        fakeUserRepository.create(reportedUser)

        val board = fakeBoardRepository.create(
            Board(
                id = 2L,
                title = "testTitle2",
                content = "testContent2",
                boardType = BoardType.BOARD,
                user = reporter,
                topic = fakeTopicRepository.create(Topic(1L, "testTopic")),
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )

        fakeBoardRepository.create(board)

        val topic = fakeTopicRepository.create(Topic(1L, "testTopic"))

        fakeTopicRepository.create(topic)


        val report = Report(
            id = 1L,
            reportTarget = ReportTarget.BOARD,
            reportTargetId = 1L,
            reporter = reporter,
            reportedUser = reportedUser,
            isCanceled = false,
            canceledAt = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        fakeReportRepository.create(report)

        // PrincipalDetails의 가짜 구현 생성
        val fakeUserDetails = PrincipalDetails(
            User(
                id = 1L,
                email = "testUser@example.com",
                hashedPassword = "hashedPassword",
                nickname = "testUser",
                notificationConsent = true,
                fcmRegistration = true,
                role = Role.USER,
                socialId = "testSocialId",
                socialType = SocialType.GOOGLE,
                userStatus = UserStatus.ACTIVE
            )
        )

        // TestingAuthenticationToken에 fakeUserDetails 설정
        val authentication = TestingAuthenticationToken(fakeUserDetails, null)
        SecurityContextHolder.getContext().authentication = authentication
    }

    afterContainer {
        // 테스트 후 SecurityContext 클리어
        SecurityContextHolder.clearContext()
    }

    Given("신고 조회 요청이 준비되었을 때") {

        When("신고 조회 요청을 하면") {
            val report = reportService.getReportById(1L)

            Then("신고 정보가 반환된다") {
                report.id shouldBe 1L
                report.reporterId shouldBe 1L
                report.reportedUserId shouldBe 2L
                report.reportTargetId shouldBe 1L
                report.isCanceled shouldBe false
                report.canceledAt shouldBe null
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
                reportList.size shouldBe 1
            }
        }
    }

})
package initTest.lyfe.lyfeBe.test.report.service

import initTest.lyfe.lyfeBe.test.mock.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.auth.PrincipalDetails
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.ReportTarget
import lyfe.lyfeBe.report.service.ReportService
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.Instant

class UpdateReportServiceTest: BehaviorSpec({

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


        val admin = User(
            id = 3L,
            email = "admin@example.com",
            hashedPassword = "hashedPassword",
            nickname = "admin",
            notificationConsent = true,
            fcmRegistration = true,
            role = Role.ADMIN,
            socialId = "testSocialId",
            socialType = SocialType.GOOGLE,
            userStatus = UserStatus.ACTIVE
        )


        fakeUserRepository.create(reporter)
        fakeUserRepository.create(reportedUser)
        fakeUserRepository.create(admin)

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

        val fakeAdminDetails = PrincipalDetails(
            User(
                id = 3L,
                email = "admin@example.com",
                hashedPassword = "hashedPassword",
                nickname = "admin",
                notificationConsent = true,
                fcmRegistration = true,
                role = Role.ADMIN,
                socialId = "testSocialId",
                socialType = SocialType.GOOGLE,
                userStatus = UserStatus.ACTIVE
            )
        )

        // TestingAuthenticationToken에 fakeUserDetails 설정
        val authentication = TestingAuthenticationToken(fakeAdminDetails, null)
        SecurityContextHolder.getContext().authentication = authentication

    }

    afterContainer {
        // 테스트 후 SecurityContext 클리어
        SecurityContextHolder.clearContext()

        fakeBoardRepository.clear()
        fakeUserRepository.clear()
        fakeTopicRepository.clear()
        fakeCommentRepository.clear()
        fakeReportRepository.clear()
    }

    Given("Report Data가 준비되었을 때") {

        When("신고를 취소할 때") {
            reportService.cancelReport(1L)
            val cancelReport = reportService.getReportById(1L)

            Then("신고가 취소되었는지 확인할 때") {
                cancelReport.isCanceled shouldBe true
            }
        }
    }
})

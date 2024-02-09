package initTest.lyfe.lyfeBe.test.report.service

import initTest.lyfe.lyfeBe.test.mock.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.auth.PrincipalDetails
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.report.ReportCreate
import lyfe.lyfeBe.report.ReportTarget
import lyfe.lyfeBe.report.service.ReportService
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.Instant

class CreateReportServiceTest: BehaviorSpec({

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

        fakeBoardRepository.clear()
        fakeUserRepository.clear()
        fakeTopicRepository.clear()
        fakeCommentRepository.clear()
        fakeReportRepository.clear()
    }


    Given("ReportCreate가 준비되었을 때") {

        val reportCreate = ReportCreate(
            reportTarget = ReportTarget.BOARD,
            reportTargetId = 2L,
        )

        When("신고 생성 요청을 처리할 때") {
            val result = reportService.createReport(reportCreate)

            Then("신고가 생성되어야 한다") {
                val report = fakeReportRepository.getById(result.id)
                report.reportTarget shouldBe reportCreate.reportTarget
                report.reportTargetId shouldBe reportCreate.reportTargetId
            }
        }
    }
})
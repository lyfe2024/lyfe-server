package initTest.lyfe.lyfeBe.test.report.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.auth.PrincipalDetails
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.report.ReportTarget
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.Instant

class UpdateReportControllerTest : BehaviorSpec({

    val testContainer = TestContainer.build()

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


        testContainer.userRepository.create(reporter)
        testContainer.userRepository.create(reportedUser)
        testContainer.userRepository.create(admin)

        val board = testContainer.boardRepository.create(
            Board(
                id = 2L,
                title = "testTitle2",
                content = "testContent2",
                boardType = BoardType.BOARD,
                user = reporter,
                topic = testContainer.topicRepository.create(Topic(1L, "testTopic")),
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )

        testContainer.boardRepository.create(board)

        val topic = testContainer.topicRepository.create(Topic(1L, "testTopic"))

        testContainer.topicRepository.create(topic)


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

        testContainer.reportRepository.create(report)

    }

    afterContainer {
        // 테스트 후 SecurityContext 클리어
        SecurityContextHolder.clearContext()
    }

    Given("어드민이 아닌 유저가 신고 수정 요청을 할 때"){

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

    Given("신고 수정 요청이 준비되었을 때") {

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

        When("신고 취소 요청을 보내면") {
            testContainer.reportController.cancelReport(1L).result
            val report = testContainer.reportRepository.getById(1L)

            Then("신고 취소인 isCanceled 가 true 가 된다. ") {
                report.isCanceled shouldBe true
            }

            When("이미 신고 취소된 신고를 다시 신고 취소 요청을 보내면") {
                testContainer.reportController.cancelReport(1L).result
                testContainer.reportController.cancelReport(1L).result
                val report = testContainer.reportRepository.getById(1L)

                Then("이미 신고 취소된 신고를 다시 신고 취소 요청을 보내면 isCanceled 가 false 가 된다. ") {
                    report.isCanceled shouldBe false
                }
            }
        }
    }

})

package initTest.lyfe.lyfeBe.test.report.controller

import initTest.lyfe.lyfeBe.test.board.BoardFactory
import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.report.ReportFactory
import initTest.lyfe.lyfeBe.test.user.UserFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.auth.PrincipalDetails
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.report.Report
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.Instant

class UpdateReportControllerTest : BehaviorSpec({

    val testContainer = TestContainer.build()
    lateinit var board: Board
    lateinit var topic: Topic
    lateinit var report: Report

    beforeContainer {

        val reporter = UserFactory.createTestUser()
        val reportedUser = UserFactory.createTestUser(2)
        testContainer.userRepository.create(reporter)
        testContainer.userRepository.create(reportedUser)


        board = BoardFactory.createTestBoard()
        testContainer.boardRepository.create(board)

        topic = Topic(1L, "testTopic")
        testContainer.topicRepository.create(topic)

        report = ReportFactory.createTestReport()
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
                profileUrl = "testProfileUrl",
                socialId = "testSocialId",
                socialType = SocialType.GOOGLE,
                userStatus = UserStatus.ACTIVE,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )

        // TestingAuthenticationToken에 fakeUserDetails 설정
        val authentication = TestingAuthenticationToken(fakeUserDetails, null)
        SecurityContextHolder.getContext().authentication = authentication

    }

    Given("신고 수정 요청이 준비되었을 때") {

        val admin = User(
            id = 3L,
            email = "admin@example.com",
            hashedPassword = "hashedPassword",
            nickname = "admin",
            notificationConsent = true,
            fcmRegistration = true,
            role = Role.ADMIN,
            profileUrl = "testProfileUrl",
            socialId = "testSocialId",
            socialType = SocialType.GOOGLE,
            userStatus = UserStatus.ACTIVE,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        testContainer.userRepository.create(admin)
        UserFactory.setSecurityContextUser(admin)

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

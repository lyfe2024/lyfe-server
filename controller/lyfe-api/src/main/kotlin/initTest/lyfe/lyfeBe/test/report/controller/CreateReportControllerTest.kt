package initTest.lyfe.lyfeBe.test.report.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.auth.PrincipalDetails
import lyfe.lyfeBe.auth.SocialType
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.report.ReportTarget
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.web.report.req.SaveReportRequest
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.Instant

class CreateReportControllerTest : BehaviorSpec({

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


        testContainer.userRepository.create(reporter)
        testContainer.userRepository.create(reportedUser)

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

    Given(" 신고 생성 요청이 준비되었을 때") {

        val req = SaveReportRequest(
            reportTarget = ReportTarget.BOARD,
            reportTargetId = 2L
        )

        When("신고 생성 요청을 처리할 하고 조회 했을때") {


            val reportId = testContainer.reportController.createReport(req)
            val report = testContainer.reportService.getReportById(reportId.result.id)


            Then("생성된 신고의 속성이 요청과 일치하는지 확인할 때") {
                report.reportTargetType shouldBe req.reportTarget.toString()
                report.reportTargetId shouldBe req.reportTargetId
            }
        }

    }
})
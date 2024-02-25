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
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.Instant

class GetReportControllerTest: BehaviorSpec({

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
            userStatus = UserStatus.ACTIVE,
            profileUrl = ""
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
            userStatus = UserStatus.ACTIVE,
            profileUrl = ""
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


        val report = Report(
            id = 1L,
            reportTarget = ReportTarget.BOARD,
            reportTargetId = 2L,
            reporter = reporter,
            reportedUser = reportedUser,
            isCanceled = false,
            canceledAt = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        testContainer.reportRepository.create(report)

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
                userStatus = UserStatus.ACTIVE,
                profileUrl = ""
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


        When("신고를 조회 했을 때") {
            val res = testContainer.reportController.getReport(1L).result

            Then("저장된 신고의 필드와 응답값과 일치해야 한다.") {
                res.reportTargetId shouldBe 2L
                res.reportedUserNickname shouldBe "testUser2"
            }
        }
    }

//    Given("신고 리스트 조회를 위한 데이터가 준비되엇을 때"){
//
//        val pageable = PageRequest.of(
//            0, // 페이지 번호 (0부터 시작)
//            5, // 페이지 크기
//            Sort.by("id").descending()
//        )
//
//        When("신고 리스트를 조회 했을 때") {
//            val res = testContainer.reportController.getReports(0, pageable).result
//
//            Then("저장된 신고의 필드와 응답값과 일치해야 한다.") {
//                res.size shouldBe 1
//                res[0].reportTargetId shouldBe 1L
//                res[0].reportedUserNickname shouldBe "testUser2"
//            }
//        }
//    }
})
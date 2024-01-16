package initTest.lyfe.lyfeBe.test.comment.controller

import initTest.lyfe.lyfeBe.test.mock.FakeCommentRepository
import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.web.comment.req.SaveCommentRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.Instant

class GetCommentControllerTest(
): BehaviorSpec({

    val testContainer = TestContainer.build()


    beforeContainer {

        val user = User(
            id = 1L,
            email = "testUser@example.com",
            hashedPassword = "hashedPassword",
            nickname = "testUser",
            notificationConsent = true,
            fcmRegistration = true,
            role = Role.USER,
            socialId = "testSocialId",
            socialType = lyfe.lyfeBe.auth.SocialType.GOOGLE,
            userStatus = UserStatus.ACTIVE
        )
        testContainer.userRepository.create(user)

        val topic = Topic(1L, "testTopic")
        testContainer.topicRepository.create(topic)


        val board = Board(
            id = 1L,
            title = "testTitle",
            content = "testContent",
            boardType = BoardType.BOARD,
            user = user,
            topic = topic,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        testContainer.boardRepository.create(board)

    }

    Given("댓글 조회 요청이 준비되었을 때") {

        val req = SaveCommentRequest(
            content = "테스트 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다.",
            commentGroupId = null
        )

        testContainer.commentController.create(req, 1L)

        When("댓글을 조회 했을 때") {
            val res = testContainer.commentController.getComment(1L).result

            Then("저장된 댓글의 필드와 응답값과 일치해야 한다.") {
                res.commentGroupId shouldBe null
                res.content shouldBe req.content
                res.user.username shouldBe "testUser"
            }
        }
    }

    Given("댓글 리스트 조회를 위한 데이터가 준비되엇을 때"){



        val req = SaveCommentRequest(
            content = "테스트 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다.",
            commentGroupId = 1L
        )

        testContainer.commentController.create(req, 1L)


        val pageable = PageRequest.of(
            0, // 페이지 번호 (0부터 시작)
            5, // 페이지 크기
            Sort.by("id").descending()
        )

        When("댓글 리스트를 조회 했을 때"){
            val res = testContainer.commentController.getLatestCommentList(1L, 1L , pageable).result

            Then("저장된 댓글의 필드와 응답값과 일치해야 한다."){
                res.forEach{
//                    it.commentGroupId shouldBe req.commentGroupId
                    it.content shouldBe req.content
                    it.user.nickname shouldBe "testUser"
                }
            }
        }
    }

})
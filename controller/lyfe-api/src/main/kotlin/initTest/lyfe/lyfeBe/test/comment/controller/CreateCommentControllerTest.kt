package initTest.lyfe.lyfeBe.test.comment.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.web.comment.req.SaveCommentRequest
import java.time.Instant

class CreateCommentControllerTest (
) : BehaviorSpec({

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
            profileUrl = "https://example.com/image.jpg",

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
            topic = Topic(
                id = 1L,
                content = "testTopic"
            ),
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        testContainer.boardRepository.create(board)
    }

    Given("댓글 생성 요청이 준비되었을 때") {

        val req = SaveCommentRequest(
            content = "테스트 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다.",
            commentGroupId = null
        )

        val commentId = testContainer.commentController.create(req, 1L)

        val comment = testContainer.commentService.getById(commentId.result.id)

        When("생성된 댓글의 id가 일치해야 한다") {

            Then("해당 댓글의 id와 본문이 일치한다.") {
                comment.id shouldBe commentId.result.id
                comment.content shouldBe req.content
            }
        }

    }


})

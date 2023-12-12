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
import lyfe.lyfeBe.web.comment.req.UpdateCommentRequest
import java.time.Instant

class UpdateCommentControllerTest(
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
            userStatus = UserStatus.ACTIVE,
            visibility = true
        )
        testContainer.userRepository.create(user)

        val topic = Topic(1L, "testTopic", visibility = true)
        testContainer.topicRepository.create(topic)


        val board = Board(
            id = 1L,
            title = "testTitle",
            content = "testContent",
            boardType = BoardType.BOARD,
            user = user,
            topic = topic,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            visibility = true
        )
        testContainer.boardRepository.create(board)

    }

    Given("댓글 수정 요청이 준비되었을 때") {

        val req = SaveCommentRequest(
            content = "테스트 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다.",
            commentGroupId = null
        )

       testContainer.createCommentController.create(req, 1L)

        When("댓글을 업데이트 했을 때") {

            val updateReq = UpdateCommentRequest(
                content = "바뀐 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다."
            )

            val commentId = testContainer.updateCommentController.update(1L, updateReq)
            println(commentId.result.id)

            val comment = testContainer.commentService.getById(commentId.result.id)

            println(comment.content)
            Then("업데이트된 댓글의 내용이 요청된 값과 일치해야 한다") {
                comment.content shouldBe updateReq.content
            }
        }
    }
})
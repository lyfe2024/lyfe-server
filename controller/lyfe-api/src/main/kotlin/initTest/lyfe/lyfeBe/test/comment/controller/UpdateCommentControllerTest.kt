package initTest.lyfe.lyfeBe.test.comment.controller

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createTestBoard
import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.setSecurityContextUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.web.comment.req.SaveCommentRequest
import lyfe.lyfeBe.web.comment.req.UpdateCommentRequest
import org.springframework.security.core.context.SecurityContextHolder

class UpdateCommentControllerTest(
): BehaviorSpec({

    val testContainer = TestContainer.build()
    lateinit var board: Board
    lateinit var topic: Topic
    lateinit var user: User

    beforeContainer {

        user = createTestUser()
        testContainer.userRepository.create(user)

        topic = Topic(1L, "testTopic")
        testContainer.topicRepository.create(topic)


        board = createTestBoard()
        testContainer.boardRepository.create(board)

        setSecurityContextUser(user)

    }

    afterContainer {
        SecurityContextHolder.clearContext()
    }

    Given("댓글 수정 요청이 준비되었을 때") {

        val req = SaveCommentRequest(
            content = "테스트 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다.",
            commentGroupId = null
        )

        testContainer.commentController.create(req, 1L)

        When("댓글을 업데이트 했을 때") {

            val updateReq = UpdateCommentRequest(
                content = "바뀐 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다."
            )

            val commentId = testContainer.commentController.update(1L, updateReq)

            val comment = testContainer.commentService.getById(commentId.result.id)

            println(comment.content)
            Then("업데이트된 댓글의 내용이 요청된 값과 일치해야 한다") {
                comment.content shouldBe updateReq.content
            }
        }
    }
})
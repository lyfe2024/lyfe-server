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
import org.springframework.security.core.context.SecurityContextHolder

class CreateCommentControllerTest (
) : BehaviorSpec({

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
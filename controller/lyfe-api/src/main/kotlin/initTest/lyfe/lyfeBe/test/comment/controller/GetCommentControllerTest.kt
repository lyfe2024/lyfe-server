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
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.SecurityContextHolder

class GetCommentControllerTest(
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


    Given("댓글 조회 요청이 준비되었을 때") {

        val req = SaveCommentRequest(
            content = "테스트 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다.",
            commentGroupId = null
        )

        val comment = testContainer.commentController.create(req, board.id)

        When("댓글을 조회 했을 때") {
            val res = testContainer.commentController.getComment(comment.result.id).result

            Then("저장된 댓글의 필드와 응답값과 일치해야 한다.") {
                res.commentGroupId shouldBe null
                res.content shouldBe req.content
                res.user.username shouldBe user.nickname
            }
        }
    }

    Given("댓글 리스트 조회를 위한 데이터가 준비되엇을 때") {


        val req = SaveCommentRequest(
            content = "테스트 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다.",
            commentGroupId = 1L
        )

        testContainer.commentController.create(req, board.id)


        val pageable = PageRequest.of(
            0, // 페이지 번호 (0부터 시작)
            5, // 페이지 크기
            Sort.by("id").descending()
        )

        When("댓글 리스트를 조회 했을 때") {
            val res = testContainer.commentController.getLatestCommentList(board.id, 1L, pageable)
                .result.list

            Then("저장된 댓글의 필드와 응답값과 일치해야 한다.") {
                res.forEach {
                    it.content shouldBe req.content
                    it.user.username shouldBe user.nickname
                }
            }
        }
    }

})
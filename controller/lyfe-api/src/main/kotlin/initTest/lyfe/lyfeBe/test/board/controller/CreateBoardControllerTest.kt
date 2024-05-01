package initTest.lyfe.lyfeBe.test.board.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.user.UserFactory
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.web.board.req.BoardSaveRequest
import java.time.Instant


class CreateBoardControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()
    lateinit var topic: Topic
    lateinit var user: User

    beforeContainer {

        user = createTestUser()
        testContainer.userRepository.create(user)

        topic = Topic(1L, "testTopic", Instant.now(), Instant.now())
        testContainer.topicRepository.create(topic)

        UserFactory.setSecurityContextUser(user)
    }



    Given("게시판 생성 요청이 준비되었을 때") {

        val req = BoardSaveRequest(
            title = "테스트 게시판 제목",
            content = "테스트 내용입니다. 여기에 게시판 내용이 들어갑니다.",
            boardType = BoardType.BOARD,
            topicId = 1L
        )

        val boardId = testContainer.boardController.create(req)

        val board = testContainer.boardRepository.getById(boardId.result.id)


        When("생성된 게시판의 제목, 내용, 유형이 요청과 일치해야 한다") {

            Then("해당 보드 타이틀과 본문이 일치한다.") {
                board.title shouldBe req.title
                board.content shouldBe req.content
                board.boardType shouldBe req.boardType
                board.topic.id shouldBe req.topicId
                board.user.id shouldBe user.id
                board.createdAt shouldBe board.updatedAt
                board.imageUrl shouldBe ""
            }
        }
    }
})
package initTest.lyfe.lyfeBe.test.board.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.user.UserFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.web.board.req.BoardSaveRequest
import lyfe.lyfeBe.web.board.req.BoardUpdateRequest


class UpdateBoardControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()
    val user = UserFactory.createTestUser()
    val topic = Topic(1L, "testTopic")


    beforeContainer {

        testContainer.userRepository.create(user)

        testContainer.topicRepository.create(topic)

    }

    Given("게시판 생성을 위한 요청 데이터가 준비되었을 때") {

        val req = BoardSaveRequest(
            title = "테스트 게시판 제목",
            content = "테스트 내용입니다. 여기에 게시판 내용이 들어갑니다.",
            boardType = BoardType.BOARD,
            userId = 1L,
            topicId = 1L
        )

        testContainer.boardController.create(req)

        When("게시판을 업데이트 했을 때") {

            val updateReq = BoardUpdateRequest(
                title = "바뀐  게시판 제목",
                content = "바뀐 테스트 내용입니다. 여기에 게시판 내용이 들어갑니다."
            )

            val boardId = testContainer.boardController.update(1L, updateReq,user)

            val board = testContainer.boardService.getById(boardId.result.id)

            Then("업데이트된 게시판의 제목과 내용이 요청된 값과 일치해야 한다") {
                board.title shouldBe updateReq.title
                board.content shouldBe updateReq.content
            }
        }
    }

})
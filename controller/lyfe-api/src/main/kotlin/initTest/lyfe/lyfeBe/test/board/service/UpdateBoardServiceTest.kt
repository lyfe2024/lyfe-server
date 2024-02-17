package initTest.lyfe.lyfeBe.test.board.service

import initTest.lyfe.lyfeBe.test.board.BoardFactory
import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createBoardCreate
import initTest.lyfe.lyfeBe.test.mock.*
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.web.topic.TopicFactory.Companion.createTestTopic


class UpdateBoardServiceTest(
) : BehaviorSpec({

    val fakeBoardRepository = FakeBoardRepository()
    val fakeUserRepository = FakeUserRepository()
    val fakeTopicRepository = FakeTopicRepository()
    val fakeWhiskyRepository = FakeWhiskyRepository()
    val fakeCommentRepository = FakeCommentRepository()
    val boardService = BoardService(
        fakeBoardRepository,
        fakeUserRepository,
        fakeTopicRepository,
        fakeWhiskyRepository,
        fakeCommentRepository
    )

    beforeContainer {
        // 테스트에 필요한 사용자, 토픽, 게시물을 미리 생성하고 저장
        val user = createTestUser()

        fakeUserRepository.create(user)

        val topic = createTestTopic()

        fakeTopicRepository.create(topic)




    }

    afterContainer {
        fakeBoardRepository.clear()
        fakeUserRepository.clear()
        fakeTopicRepository.clear()
        fakeWhiskyRepository.clear()
        fakeCommentRepository.clear()
    }



    Given("게시판 업데이트 요청이 준비되었을 때") {


        val boardCreate = createBoardCreate()
        val board = boardService.create(boardCreate)
        val boardUpdate = BoardFactory.createBoardUpdate(boardId = board.id)


        When("게시판 업데이트 요청을 처리할 때") {

            val boardId = boardService.update(boardUpdate)

            val newBoard = fakeBoardRepository.getById(boardId.id)

            Then("업데이트된 게시판의 속성이 업데이트 요청과 일치하는지 확인할 때") {
                newBoard.title shouldBe boardUpdate.title
                newBoard.content shouldBe boardUpdate.content
            }

        }
    }

})
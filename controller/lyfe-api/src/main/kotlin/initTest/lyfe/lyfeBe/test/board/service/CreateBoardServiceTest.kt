package initTest.lyfe.lyfeBe.test.board.service

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createBoardCreate
import initTest.lyfe.lyfeBe.test.mock.*
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.web.topic.TopicFactory.Companion.createTestTopic


class CreateBoardServiceTest(
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


    Given("게시판 생성 요청과 사용자, 주제가 준비되었을 때") {

        val boardCreate = createBoardCreate()


        When("게시판 생성 요청을 처리할 때") {

            val newBoardId = boardService.create(boardCreate)

            val newBoard = fakeBoardRepository.getById(newBoardId.id)

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                newBoard.title shouldBe boardCreate.title
                newBoard.content shouldBe boardCreate.content
                newBoard.boardType shouldBe boardCreate.boardType
                newBoard.user.id shouldBe boardCreate.userId
                newBoard.topic.id shouldBe boardCreate.topicId
            }
        }
    }
})
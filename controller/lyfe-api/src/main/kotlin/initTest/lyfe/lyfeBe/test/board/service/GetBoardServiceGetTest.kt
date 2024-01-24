package initTest.lyfe.lyfeBe.test.board.service

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createBoardsGet
import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createTestBoard
import initTest.lyfe.lyfeBe.test.comment.CommentFactory.Companion.createTestComment
import initTest.lyfe.lyfeBe.test.mock.*
import initTest.lyfe.lyfeBe.test.user.UserFactory
import initTest.lyfe.lyfeBe.test.whisky.WhiskyFactory.Companion.createTestWhisky
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardGet
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.BoardsGet
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.whisky.Whisky
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort


class GetBoardServiceGetTest(
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

     lateinit var user: User
     lateinit var topic: Topic
     lateinit var board: Board
     lateinit var whisky: Whisky
     lateinit var comment: Comment


    beforeContainer {

         user = UserFactory.createTestUser()

        fakeUserRepository.create(user)

         topic = Topic(0, "testTopic")
        fakeTopicRepository.create(topic)

         board = createTestBoard()

        fakeBoardRepository.create(board)

         whisky = createTestWhisky(user = user, board = board)

        fakeWhiskyRepository.create(whisky)

         comment = createTestComment(user = user, board = board)

        fakeCommentRepository.create(comment)
    }

    afterContainer {
        fakeCommentRepository.clear()
    }



    Given("게시판 생성이 준비되고 코멘트랑 조아요가 준비 됬을 때 실행되었을 때") {


        When("생성된 게시판의 상세 정보를 조회할 때") {

            val boardDto = boardService.get(BoardGet(1L))

            Then("조회된 게시판의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                boardDto.title shouldBe board.title
                boardDto.content shouldBe board.content
                boardDto.boardType shouldBe board.boardType
                boardDto.user.id shouldBe board.user.id
                boardDto.whiskyCount shouldBe "1"
                boardDto.commentCount shouldBe "1"
            }
        }
    }

    Given("게시판 생성 복수요청이 준비되고 실행되었을 때") {
        val cursorId = Long.MAX_VALUE

        val pageable = PageRequest.of(
            0, // 페이지 번호 (0부터 시작)
            5, // 페이지 크기
            Sort.by("id").descending()
        )

        val boardsGet = createBoardsGet(
            boardId = cursorId,
            pageable = pageable

        )


        When("생성된 여러 게시판의 정보를 조회할 때(id내림차순으로) ") {

            val boardDtos = boardService.getBoards(boardsGet)

            Then("조회된 게시판의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                boardDtos.forEach { boardDto ->
                    boardDto.title shouldBe board.title
                    boardDto.content shouldBe board.content
                    boardDto.boardType shouldBe board.boardType
                    boardDto.user.id shouldBe board.user.id
                    boardDto.whiskyCount shouldBe "1"
                    boardDto.commentCount shouldBe "1"
                }
            }

        }
    }
})
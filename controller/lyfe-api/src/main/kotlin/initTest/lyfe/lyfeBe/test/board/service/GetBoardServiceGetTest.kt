package initTest.lyfe.lyfeBe.test.board.service

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createBoardsGet
import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createPopularBoard
import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createTestBoard
import initTest.lyfe.lyfeBe.test.comment.CommentFactory.Companion.createTestComment
import initTest.lyfe.lyfeBe.test.mock.*
import initTest.lyfe.lyfeBe.test.user.UserFactory
import initTest.lyfe.lyfeBe.test.whisky.WhiskyFactory.Companion.createTestWhisky
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.fomatter.CursorGenerator.Companion.createCursorValue
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.whisky.Whisky
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.Instant


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
    lateinit var board1: Board
    lateinit var board2: Board
    lateinit var whisky: Whisky
    lateinit var comment: Comment

    val testDate = Instant.now() // 테스트 시작 시간


    beforeContainer {

        user = UserFactory.createTestUser()

        fakeUserRepository.create(user)

        topic = Topic(0, "testTopic")
        fakeTopicRepository.create(topic)

        board1 = createTestBoard(
            id = 1,
            boardType = BoardType.BOARD_PICTURE,
            createdAt = testDate
        )
        board2 = createTestBoard(
            id = 2,
            boardType = BoardType.BOARD_PICTURE,
            title = "2번째보드입니다",
            createdAt = testDate
        )

        fakeBoardRepository.create(board1)
        fakeBoardRepository.create(board2)




        whisky = createTestWhisky(user = user, board = board1)

        fakeWhiskyRepository.create(whisky)
        fakeWhiskyRepository.create(whisky)

        comment = createTestComment(user = user, board = board1)

        fakeCommentRepository.create(comment)
    }

    afterContainer {
        fakeBoardRepository.clear()
        fakeWhiskyRepository.clear()
        fakeCommentRepository.clear()
    }



    Given("게시판 생성이 준비되고 코멘트랑 좋아요가 준비 됬을 때 실행되었을 때") {


        When("생성된 게시판의 상세 정보를 조회할 때") {

            val boardDto = boardService.get(BoardGet(1L))

            Then("조회된 게시판의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                boardDto.title shouldBe board1.title
                boardDto.content shouldBe board1.content
                boardDto.boardType shouldBe board1.boardType
                boardDto.user.id shouldBe board1.user.id
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
            pageable = pageable,
            type = BoardType.BOARD_PICTURE,
            testDate.toString()

        )

    }

    Given("게시판 생성 복수요청이 준비되고 실행되었을 때(최신글)") {
        val cursorId = Long.MAX_VALUE

        val pageable = PageRequest.of(
            0, // 페이지 번호 (0부터 시작)
            5, // 페이지 크기
            Sort.by("id").descending()
        )

        val boardsGet = createBoardsGet(
            boardId = cursorId,
            pageable = pageable,
            type = BoardType.BOARD_PICTURE,
            testDate.toString()
        )

        When("생성된 여러 게시판의 정보를 조회할 때(id내림차순으로) ") {
            val boardDtos = boardService.getBoards(boardsGet)

            Then("조회된 게시판이 ID 기반으로 내림차순 정렬되어야 한다") {
                boardDtos.zipWithNext().forEach { (current, next) ->
                    current.id shouldBeGreaterThan next.id
                }
            }
        }
    }


    Given("게시판 생성 복수요청이 준비되고 실행되었을 때(인기게시글)") {
        val whiskyCount = 5L
        val pageCount = 2

        val pageable = PageRequest.of(
            0, // 페이지 번호 (0부터 시작)
            5, // 페이지 크기
            Sort.by("id").descending()
        )

        val boardsPopularGet = createPopularBoard(
            testDate.toString(),
            whiskyCount = whiskyCount,
            type = BoardType.BOARD_PICTURE,
            count = pageCount

        )

        When("위스키 카운트를 기준으로 게시판 정보를 조회할 때") {
            val boardDtos = boardService.getPopularBoards(boardsPopularGet)

            Then("조회된 게시판이 위스키 카운트 기반으로 내림차순 정렬되어야 한다") {
                boardDtos.zipWithNext().forEach { (current, next) ->
                    if (current.whiskyCount == next.whiskyCount) {
                        current.id shouldBeGreaterThan next.id
                    } else {
                        current.whiskyCount shouldBeGreaterThan next.whiskyCount
                    }
                }
            }
        }
    }
})
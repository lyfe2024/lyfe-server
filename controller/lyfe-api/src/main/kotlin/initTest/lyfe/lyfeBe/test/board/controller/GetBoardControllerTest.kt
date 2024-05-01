package initTest.lyfe.lyfeBe.test.board.controller

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createBoardsSaveRequest
import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.user.UserFactory
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.Constants.Companion.CURSOR_VALUE
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.PopularType
import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.Instant
import java.time.LocalDate


class GetBoardControllerTest(
) : BehaviorSpec({
    val testContainer = TestContainer.build()

    lateinit var user: User
    lateinit var topic: Topic

    beforeContainer {

        user = createTestUser(id = 1L)
        testContainer.userRepository.create(user)

        topic = Topic(id = 1L,
            content = "testTopic" ,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            appliedAt = LocalDate.now()
        )
        testContainer.topicRepository.create(topic)

        UserFactory.setSecurityContextUser(user)
    }


    Given("게시판 생성을 위한 요청 데이터가 준비되었을 때") {

        val req = createBoardsSaveRequest()

        testContainer.boardController.create(req)

        When("게시판을 조회 했을 때") {
            val res = testContainer.boardController.get(1L).result

            Then("저장된 게시판의 필드와 응답값 과 일치해야 한다") {
                res.title shouldBe req.title
                res.content shouldBe req.content
                res.boardType shouldBe req.boardType
            }
        }
    }

    Given("게시판 리스트 조회를 위한 데이터가 준비되었을 때(id최신순)") {

        val testCursorId = Long.MAX_VALUE

        val req = createBoardsSaveRequest()

        testContainer.boardController.create(req)
        testContainer.boardController.create(req)

        When("게시판 리스트를 조회 했을 때") {

            val of = PageRequest.of(
                0, // 페이지 번호 (0부터 시작)
                5, // 페이지 크기
                Sort.by("id").descending()
            )
            val res: List<BoardDto> = testContainer.boardController.getLatestBoards(
                cursorId = testCursorId,
                date = null,
                type = BoardType.BOARD,
                pageable = of
            ).result.list

            Then("저장된 게시판의 필드와 응답값 과 일치해야 한다") {
                res.forEach { board ->
                    board.title shouldBe req.title
                    board.content shouldBe req.content
                    board.boardType shouldBe req.boardType
                }
            }
            And("조회된 게시판이 ID 기반으로 내림차순 정렬되어야 한다") {
                res.zipWithNext().forEach { (current, next) ->
                    current.id shouldBeGreaterThan next.id
                }
            }
        }
    }


    Given("게시판 리스트 조회를 위한 데이터가 준비되었을 때(인기순 whiskyCount)") {

        val req = createBoardsSaveRequest()
        val boardId1 = testContainer.boardController.create(req).result.id

        testContainer.whiskyController.likeWhiskyByBoardId(boardId = boardId1)

        When("게시판 리스트를 조회 했을 때") {


            val res: List<BoardDto> = testContainer.boardController.getPopularBoards(
                cursorId = CURSOR_VALUE,
                popularType = PopularType.WHISKY,
                pageable = PageRequest.of(
                    0, // 페이지 번호 (0부터 시작)
                    5, // 페이지 크기
                    Sort.by("id").descending()
                ),
                type = BoardType.BOARD
            ).result.list

            Then("저장된 게시판의 필드와 응답값 과 일치해야 한다") {
                res.forEach { board ->
                    board.title shouldBe req.title
                    board.content shouldBe req.content
                    board.boardType shouldBe req.boardType
                }
            }
            And("조회된 게시판이 whiskyCount 기반으로 내림차순 정렬되어야 한다") {
                res.zipWithNext().forEach { (current, next) ->
                    current.id shouldBeGreaterThan next.id
                }
            }

        }
    }

    Given("게시판 리스트 조회를 위한 데이터가 준비되었을 때(User Boards)") {

        val testCursorId = Long.MAX_VALUE

        val req = createBoardsSaveRequest()

        testContainer.boardController.create(req)
        testContainer.boardController.create(req)

        When("게시판 리스트를 조회 했을 때") {

            val of = PageRequest.of(
                0, // 페이지 번호 (0부터 시작)
                5, // 페이지 크기
                Sort.by("id").descending()
            )

            val res: List<BoardDto> = testContainer.boardController.getMyBoards(
                cursorId = testCursorId,
                type = req.boardType,
                pageable = of,
            ).result.list

            Then("저장된 게시판의 필드와 응답값 과 일치해야 한다") {
                res.forEach { board ->
                    board.title shouldBe req.title
                    board.content shouldBe req.content
                    board.boardType shouldBe req.boardType
                }
            }
            And("조회된 게시판이 ID 기반으로 내림차순 정렬되어야 한다") {
                res.zipWithNext().forEach { (current, next) ->
                    current.id shouldBeGreaterThan next.id
                }
            }
        }
    }
})
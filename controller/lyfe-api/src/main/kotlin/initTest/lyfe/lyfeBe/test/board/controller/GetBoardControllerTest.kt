package initTest.lyfe.lyfeBe.test.board.controller

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createBoardsSaveRequest
import initTest.lyfe.lyfeBe.test.mock.TestContainer
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.web.board.req.BoardSaveRequest
import lyfe.lyfeBe.web.whisky.req.WhiskySaveRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort


class GetBoardControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()

    lateinit var user1: User
    lateinit var user2: User
    lateinit var user3: User
    lateinit var topic: Topic

    beforeContainer {

        user1 = createTestUser(id = 1L)
        user2 = createTestUser(id = 2L)
        user3 = createTestUser(id = 3L)
        testContainer.userRepository.create(user1)
        testContainer.userRepository.create(user2)
        testContainer.userRepository.create(user3)

        topic = Topic(1L, "testTopic")
        testContainer.topicRepository.create(topic)
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
                res.user.id shouldBe req.userId
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
            val res: List<BoardDto> = testContainer.boardController.getBoards(
                testCursorId,
                date = null,
                type = BoardType.BOARD,
                pageable = of
            ).result

            Then("저장된 게시판의 필드와 응답값 과 일치해야 한다") {
                res.forEach { board ->
                    board.title shouldBe req.title
                    board.content shouldBe req.content
                    board.boardType shouldBe req.boardType
                    board.user.id shouldBe req.userId
                }
            }
            And("조회된 게시판이 ID 기반으로 내림차순 정렬되어야 한다") {
                res.zipWithNext().forEach { (current, next) ->
                    current.id shouldBeGreaterThan next.id
                }
            }
        }
    }


    Given("게시판 리스트 조회를 위한 데이터가 준비되었을 때(WhiskyCount 내림차순)") {

        val testWhiskyCount = 5L
        val testPageCount = 5

        val req = createBoardsSaveRequest()


        val boardId1 = testContainer.boardController.create(req).result.id
        val boardId2 = testContainer.boardController.create(req).result.id

        testContainer.whiskyController.create(WhiskySaveRequest(userId = user1.id, boardId1))
        testContainer.whiskyController.create(WhiskySaveRequest(userId = user2.id, boardId1))
        testContainer.whiskyController.create(WhiskySaveRequest(userId = user3.id, boardId2))

        When("게시판 리스트를 조회 했을 때") {


            val res: List<BoardDto> = testContainer.boardController.getPopularBoards(
                whiskyCount = testWhiskyCount,
                date = null,
                count = testPageCount,
                type = BoardType.BOARD
            ).result

            Then("저장된 게시판의 필드와 응답값 과 일치해야 한다") {
                res.forEach { board ->
                    board.title shouldBe req.title
                    board.content shouldBe req.content
                    board.boardType shouldBe req.boardType
                    board.user.id shouldBe req.userId
                }
            }
            And("조회된 게시판이 whiskyCount 기반으로 내림차순 정렬되어야 한다") {
                res.zipWithNext().forEach { (current, next) ->
                    val currentCount = current.whiskyCount.toIntOrNull() ?: 0
                    val nextCount = next.whiskyCount.toIntOrNull() ?: 0

                    // 둘 다 0이면 현재 검사를 건너뛰고 다음으로 넘어갑니다.
                    if (currentCount == 0 && nextCount == 0) {
                        return@forEach // 'forEach' 루프의 다음 반복으로 넘어갑니다.
                    }
                    assert(currentCount >= nextCount) { "whiskyCount가 내림차순으로 정렬되지 않았습니다." }
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

            val res: List<BoardDto> = testContainer.boardController.getUserBoards(
                cursorId = testCursorId,
                type = req.boardType,
                pageable = of,
                user1
            ).result

            Then("저장된 게시판의 필드와 응답값 과 일치해야 한다") {
                res.forEach { board ->
                    board.title shouldBe req.title
                    board.content shouldBe req.content
                    board.boardType shouldBe req.boardType
                    board.user.id shouldBe req.userId
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
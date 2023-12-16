package initTest.lyfe.lyfeBe.test.board.service

import initTest.lyfe.lyfeBe.test.mock.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardGet
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.BoardsGet
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.image.ImageType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.whisky.Whisky
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.Instant


class GetBoardServiceGetTest(
) : BehaviorSpec({

    val fakeBoardRepository = FakeBoardRepository()
    val fakeUserRepository = FakeUserRepository()
    val fakeTopicRepository = FakeTopicRepository()
    val fakeImageRepository = FakeImageRepository()
    val fakeWhiskyRepository = FakeWhiskyRepository()
    val fakeCommentRepository = FakeCommentRepository()
    val boardService = BoardService(
        fakeBoardRepository,
        fakeUserRepository,
        fakeTopicRepository,
        fakeImageRepository,
        fakeWhiskyRepository,
        fakeCommentRepository
    )

    beforeContainer {

        val user = User(
            id = 1L,
            email = "testUser@example.com",
            hashedPassword = "hashedPassword",
            nickname = "testUser",
            notificationConsent = true,
            fcmRegistration = true,
            role = Role.USER,
            userStatus = UserStatus.ACTIVE,
            visibility = true
        )
        fakeUserRepository.create(user)

        val topic = Topic(0, "testTopic", visibility = true)
        fakeTopicRepository.create(topic)


        val image = Image(
            id = 1L,
            url = "https://example.com/image.jpg",
            board = null,
            user = user,
            type = ImageType.PROFILE,
            width = 100,
            height = 100
        )

        fakeImageRepository.create(image)

        val board = Board(
            id = 1L,
            title = "테스트 게시물",
            content = "게시물 내용입니다.",
            boardType = BoardType.BOARD,
            user = user,
            topic = topic,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            visibility = true
        )

        fakeBoardRepository.create(board)

        val whisky = Whisky(
            id = 1L,
            user = user,
            board = board,
            createdAt = Instant.now(),
        )
        fakeWhiskyRepository.create(whisky)

        val comment = Comment(
            id = 1L,
            content = "이것은 테스트 코멘트입니다.",
            commentGroupId = 1,
            user = user,
            board = board,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            visibility = true
        )

        fakeCommentRepository.create(comment)
    }

    afterContainer {
        fakeCommentRepository.clear()
    }



    Given("게시판 생성이 준비되고 코멘트랑 조아요가 준비 됬을 때 실행되었을 때") {


        When("생성된 게시판의 상세 정보를 조회할 때") {

            val boardDto = boardService.get(BoardGet(1L))

            Then("조회된 게시판의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                boardDto.title shouldBe "테스트 게시물"
                boardDto.content shouldBe "게시물 내용입니다."
                boardDto.boardType shouldBe BoardType.BOARD
                boardDto.user.id shouldBe 1L
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

        val boardsGet = BoardsGet(
            boardId = cursorId,
            pageable = pageable

        )


        When("생성된 여러 게시판의 정보를 조회할 때(id내림차순으로) ") {

            val boardDtos = boardService.getBoards(boardsGet)

            Then("조회된 게시판의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                boardDtos.forEach { boardDto ->
                    boardDto.title shouldBe "테스트 게시물"
                    boardDto.content shouldBe "게시물 내용입니다."
                    boardDto.boardType shouldBe BoardType.BOARD
                    boardDto.user.id shouldBe 1L
                    boardDto.whiskyCount shouldBe "1"
                    boardDto.commentCount shouldBe "1"
                }
            }

        }
    }
})
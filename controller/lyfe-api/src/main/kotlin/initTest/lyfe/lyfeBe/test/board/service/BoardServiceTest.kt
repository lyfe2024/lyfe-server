package initTest.lyfe.lyfeBe.test.board.service

import initTest.lyfe.lyfeBe.test.mock.FakeBoardRepository
import initTest.lyfe.lyfeBe.test.mock.FakeImageRepository
import initTest.lyfe.lyfeBe.test.mock.FakeTopicRepository
import initTest.lyfe.lyfeBe.test.mock.FakeUserRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.image.ImageType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort


class BoardServiceTest(
) : BehaviorSpec({

    val fakeBoardRepository = FakeBoardRepository()
    val fakeUserRepository = FakeUserRepository()
    val fakeTopicRepository = FakeTopicRepository()
    val fakeImageRepository = FakeImageRepository()

    val boardService = BoardService(fakeBoardRepository, fakeUserRepository, fakeTopicRepository, fakeImageRepository)

    beforeContainer {
        // 테스트에 필요한 사용자, 토픽, 게시물을 미리 생성하고 저장
        val user = User(
                id = 1,
                email = "testUser@example.com",
                hashedPassword = "hashedPassword",
                nickname = "testUser",
                notificationConsent = true,
                fcmRegistration = true,
                role = Role.USER,
                userStatus = UserStatus.ACTIVE,
        )
        fakeUserRepository.create(user)

        val topic = Topic(1, "testTopic", visibility = true)
        fakeTopicRepository.create(topic)


        val image = Image(
                id = 1L, // ID는 저장 시 자동 생성
                url = "https://example.com/image.jpg",
                board = null, // Board는 null
                user = user, // User 객체 연결
                type = ImageType.PROFILE, // ImageType 예시
                width = 100,
                height = 100
        )

         fakeImageRepository.create(image)
    }

    Given("게시판 생성 요청과 사용자, 주제가 준비되었을 때") {

        val boardCreate = BoardCreate(
                "testTile",
                "testContent",
                BoardType.BOARD,
                1L,
                1L
        )


        When("게시판 생성 요청을 처리할 때") {

            val newBoard = boardService.create(boardCreate)

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                newBoard.title shouldBe boardCreate.title
                newBoard.content shouldBe boardCreate.content
                newBoard.boardType shouldBe boardCreate.boardType
                newBoard.user.id shouldBe boardCreate.userId
                newBoard.topic.id shouldBe boardCreate.topicId
            }
        }
    }

    Given("게시판 업데이트 요청이 준비되었을 때") {

        val boardUpdate = BoardUpdate(
                1L,
                "testTile",
                "testContent"
        )

        When("게시판 업데이트 요청을 처리할 때") {

            val boardId = boardService.update(boardUpdate)

            val newBoard = fakeBoardRepository.findById(boardId)
            Then("업데이트된 게시판의 속성이 업데이트 요청과 일치하는지 확인할 때") {
                newBoard.title shouldBe boardUpdate.title
                newBoard.content shouldBe boardUpdate.content
            }

        }
    }

    Given("게시판 생성 요청이 준비되고 실행되었을 때") {

        val boardCreate = BoardCreate(
                "testTile",
                "testContent",
                BoardType.BOARD,
                1L,
                1L
        )

        boardService.create(boardCreate)

        When("생성된 게시판의 상세 정보를 조회할 때") {

            val board = boardService.getById(1L)

            Then("조회된 게시판의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                board.title shouldBe boardCreate.title
                board.content shouldBe boardCreate.content
                board.boardType shouldBe boardCreate.boardType
                board.user.id shouldBe boardCreate.userId
                board.topic.id shouldBe boardCreate.topicId
            }
        }
    }

    Given("게시판 생성 복수요청이 준비되고 실행되었을 때") {

        val boardCreate = BoardCreate(
                "testTile",
                "testContent",
                BoardType.BOARD,
                1L,
                1L
        )

        boardService.create(boardCreate)
        boardService.create(boardCreate)

        val boardsGet = BoardsGet(
                PageRequest.of(
                        0,
                        5,
                        Sort.by(Sort.Direction.DESC, "id")
                )
        )


        When("생성된 여러 게시판의 정보를 조회할 때") {

            val boardDtos = boardService.getBoards(boardsGet)

            Then("조회된 게시판의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                boardDtos[0].title shouldBe boardCreate.title
                boardDtos[0].content shouldBe boardCreate.content
                boardDtos[0].boardType shouldBe boardCreate.boardType.toString()
                boardDtos[0].user.id shouldBe boardCreate.userId
            }
        }
    }
})
package initTest.lyfe.lyfeBe.test.board.service

import initTest.lyfe.lyfeBe.test.mock.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.image.ImageType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus


class BoardServiceCreateTest(
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
        // 테스트에 필요한 사용자, 토픽, 게시물을 미리 생성하고 저장
        val user = User(
            id = 0,
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
            id = 0,
            url = "https://example.com/image.jpg",
            board = null, // Board는 null
            user = user, // User 객체 연결ㅁ
            type = ImageType.PROFILE, // ImageType 예시
            width = 100,
            height = 100
        )

        fakeImageRepository.create(image)

    }

    afterContainer {
        fakeBoardRepository.clear()
        fakeUserRepository.clear()
        fakeTopicRepository.clear()
        fakeImageRepository.clear()
        fakeWhiskyRepository.clear()
        fakeCommentRepository.clear()
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
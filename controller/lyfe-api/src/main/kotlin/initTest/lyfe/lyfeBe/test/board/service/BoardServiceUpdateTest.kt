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


class BoardServiceUpdateTest(
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
            id = 1,
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

        val topic = Topic(1, "testTopic", visibility = true)
        fakeTopicRepository.create(topic)


        val image = Image(
            id = 1L, // ID는 저장 시 자동 생성
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



    Given("게시판 업데이트 요청이 준비되었을 때") {

        val boardCreate = BoardCreate(
            "testTile",
            "testContent",
            BoardType.BOARD,
            1L,
            1L
        )
        val newBoard = boardService.create(boardCreate)

        val boardUpdate = BoardUpdate(
            newBoard.id,
            "testTile",
            "testContent"
        )

        When("게시판 업데이트 요청을 처리할 때") {

            val boardId = boardService.update(boardUpdate)

            val newBoard = fakeBoardRepository.getById(boardId)
            Then("업데이트된 게시판의 속성이 업데이트 요청과 일치하는지 확인할 때") {
                newBoard.title shouldBe boardUpdate.title
                newBoard.content shouldBe boardUpdate.content
            }

        }
    }

})
package initTest.lyfe.lyfeBe.test.whisky.service

import initTest.lyfe.lyfeBe.test.mock.FakeBoardRepository
import initTest.lyfe.lyfeBe.test.mock.FakeUserRepository
import initTest.lyfe.lyfeBe.test.mock.FakeWhiskyRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.whisky.WhiskyCreate
import lyfe.lyfeBe.whisky.WhiskyDelete
import lyfe.lyfeBe.whisky.WhiskyService


class DeleteWhiskyServiceTest(
) : BehaviorSpec({

    val fakeBoardRepository = FakeBoardRepository()
    val fakeUserRepository = FakeUserRepository()
    val fakeWhiskyRepository = FakeWhiskyRepository()


    val whiskyService = WhiskyService(
        fakeWhiskyRepository, fakeUserRepository, fakeBoardRepository
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
            profileUrl = "https://example.com/image.jpg",
            userStatus = UserStatus.ACTIVE
        )
        fakeUserRepository.create(user)

        val board = Board(
            id = 0,
            title = "testTile",
            content = "testContent",
            boardType = BoardType.BOARD,
            user = user,
            topic = Topic(
                id = 0, content = "testTopic"
            ),
            createdAt = null,
            updatedAt = null
        )

        fakeBoardRepository.create(board)


    }

    afterContainer {
        fakeWhiskyRepository.clear()
    }


    Given("새로운 Whisky 생성을 위한 초기 데이터 준비") {

        val whisky = WhiskyCreate(
            boardId = 1L, userId = 1L
        )

        When("Whisky 서비스를 통해 새 Whisky 객체 생성 및 삭제") {

            val newWhisky = whiskyService.create(whisky)

            val whiskyDelete = WhiskyDelete(
                boardId = 1L, userId = 1L
            )

            whiskyService.delete(whiskyDelete)

            Then("삭제된 Whisky 객체 조회 시 NullPointerException 예외 발생") {
                shouldThrow<NullPointerException> {
                    fakeWhiskyRepository.get(newWhisky.whiskyId)
                }
            } //FIXME 이게 과연 필요한 테스트일까? 원래 구현체에선 멱등성 때문에 없는 아이디로도 정상동작하게했는데 어렵네
              // 컨트롤러에선 Create 만 테스트.
        }
    }
})
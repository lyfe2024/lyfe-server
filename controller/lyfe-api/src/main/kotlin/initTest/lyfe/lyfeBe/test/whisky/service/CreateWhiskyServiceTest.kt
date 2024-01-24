package initTest.lyfe.lyfeBe.test.whisky.service

import initTest.lyfe.lyfeBe.test.mock.FakeBoardRepository
import initTest.lyfe.lyfeBe.test.mock.FakeUserRepository
import initTest.lyfe.lyfeBe.test.mock.FakeWhiskyRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.whisky.WhiskyCreate
import lyfe.lyfeBe.whisky.WhiskyService


class CreateWhiskyServiceTest(
) : BehaviorSpec({

    val fakeBoardRepository = FakeBoardRepository()
    val fakeUserRepository = FakeUserRepository()
    val fakeWhiskyRepository = FakeWhiskyRepository()


    val whiskyService = WhiskyService(
        fakeWhiskyRepository,
        fakeUserRepository,
        fakeBoardRepository
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
                id = 0,
                content = "testTopic"
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
            boardId = 1L,
            userId = 1L
        )

        When("Whisky 서비스를 통해 새 Whisky 객체 생성 및 조회") {

            val newWhisky = whiskyService.create(whisky)
            val getWhisky = fakeWhiskyRepository.get(newWhisky.whiskyId)

            Then("생성된 Whisky 객체의 사용자 ID와 게시판 ID 검증") {
                getWhisky.user.id shouldBe whisky.userId
                getWhisky.board.id shouldBe whisky.boardId
            }
        }

    }

    Given("새로운 Whisky 생성을 위한 초기 데이터 준비") {
        val whisky = WhiskyCreate(
            boardId = 1L,
            userId = 1L
        )

        When("Whisky 서비스를 통해 새 Whisky 객체를 두 번 생성 시도") {
            whiskyService.create(whisky)

            Then("두 번째 생성 시도에서 IllegalStateException 예외 발생") {
                shouldThrow<IllegalStateException> {
                    whiskyService.create(whisky)
                }
            }
        }
    }
})
package initTest.lyfe.lyfeBe.test.whisky.service

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createTestBoard
import initTest.lyfe.lyfeBe.test.mock.FakeBoardRepository
import initTest.lyfe.lyfeBe.test.mock.FakeUserRepository
import initTest.lyfe.lyfeBe.test.mock.FakeWhiskyRepository
import initTest.lyfe.lyfeBe.test.user.UserFactory
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.whisky.WhiskyCreate
import lyfe.lyfeBe.whisky.WhiskyService
import lyfe.lyfeBe.whisky.dto.SaveWhiskyDto


class WhiskyServiceTest(
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
        val user = createTestUser()
        fakeUserRepository.create(user)

        UserFactory.setSecurityContextUser(user)

        val board = createTestBoard()
        fakeBoardRepository.create(board)
    }


    Given("새로운 Whisky 생성을 위한 초기 데이터 준비") {

        When("Whisky 서비스를 통해 새 Whisky 객체 생성 및 조회") {

            val newWhisky = whiskyService.createWhiskeyBoard(WhiskyCreate(1L))

            Then("생성된 Whisky 객체의 속성이 요청과 일치") {
                (newWhisky == SaveWhiskyDto(1L)) shouldBe true
            }
        }

        When("Whisky 서비스를 통해 새 Whisky 객체를 두 번 생성 시도") {
            val oldWhisky = whiskyService.createWhiskeyBoard(WhiskyCreate(1L))

            Then("생성된 Whisky 객체의 속성이 요청과 일치") {
                (oldWhisky == false) shouldBe true
            }
        }

    }
})
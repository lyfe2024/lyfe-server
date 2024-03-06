package initTest.lyfe.lyfeBe.test.whisky.service

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createTestBoard
import initTest.lyfe.lyfeBe.test.mock.FakeBoardRepository
import initTest.lyfe.lyfeBe.test.mock.FakeUserRepository
import initTest.lyfe.lyfeBe.test.mock.FakeWhiskyRepository
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import initTest.lyfe.lyfeBe.test.whisky.WhiskyFactory.Companion.createWhiskyCreate
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import lyfe.lyfeBe.whisky.WhiskyService


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

        val board = createTestBoard()

        fakeBoardRepository.create(board)
    }


    Given("좋아요 상태 토글을 위한 초기 데이터 준비") {
        val whiskyCreate = createWhiskyCreate()

        When("좋아요가 없는 상태에서 좋아요를 추가") {
            whiskyService.switchWhiskyLikeState(whiskyCreate)

            val addedWhisky = fakeWhiskyRepository.assertNoExistingWhisky(whiskyCreate.boardId, whiskyCreate.userId)

            Then("좋아요가 성공적으로 추가됨") {
                addedWhisky.shouldNotBeNull()
            }
        }

        When("좋아요가 있는 상태에서 다시 토글하여 좋아요 해제") {
            // 두번째 토글로 좋아요 삭제
            whiskyService.switchWhiskyLikeState(whiskyCreate)

            val removedWhisky = fakeWhiskyRepository.assertNoExistingWhisky(whiskyCreate.boardId, whiskyCreate.userId)

            Then("좋아요가 성공적으로 해제됨") {
                removedWhisky.shouldBeNull()
            }
        }
    }
})
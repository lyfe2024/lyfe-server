package initTest.lyfe.lyfeBe.test.whisky.model

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.whisky.Whisky


class WhiskyTest(
) : BehaviorSpec({

    Given("User, Topic 객체가 초기화되었을 때") {

        val user = User(
            id = 1L,
            email = "testUser@example.com",
            hashedPassword = "hashedPassword",
            nickname = "testUser",
            notificationConsent = true,
            fcmRegistration = true,
            role = Role.USER,
            socialId = "testSocialId",
            socialType = lyfe.lyfeBe.auth.SocialType.GOOGLE,
            userStatus = UserStatus.ACTIVE
        )
        val board = Board(
            id = 1L,
            title = "testTile",
            content = "testContent",
            boardType = BoardType.BOARD,
            user = user,
            topic = Topic(
                id = 1L,
                content = "testTopic"
            ),
            createdAt = null,
            updatedAt = null
        )


        When("user board 객체를 사용하여 새 Whisky 객체를 생성했을 때") {

            val whisky = Whisky.from(board , user)

            Then("생성된 Whisky 객체의 속성이  User , Board와 일치") {
                whisky.user.id shouldBe user.id
                whisky.user.email shouldBe user.email
                whisky.user.nickname shouldBe user.nickname
                whisky.user.role shouldBe user.role
                whisky.user.userStatus shouldBe user.userStatus

                whisky.board.id shouldBe board.id
                whisky.board.title shouldBe board.title
                whisky.board.boardType shouldBe board.boardType
                whisky.board.content shouldBe board.content
            }
        }
    }
})
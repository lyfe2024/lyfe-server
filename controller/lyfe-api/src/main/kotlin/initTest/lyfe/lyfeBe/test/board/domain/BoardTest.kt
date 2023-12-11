package initTest.lyfe.lyfeBe.test.board.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardCreate
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.BoardUpdate
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus


class BoardTest(
) : BehaviorSpec({

    Given("BoardCreate, User, Topic 객체가 초기화되었을 때") {

        val boardCreate = BoardCreate(
            "testTile",
            "testContent",
            BoardType.BOARD_CONTENT,
            1L,
            1L
        )

        val user = User(
            1L,
            "testName",
            "testEmail",
            "testPassword",
            true,
            true,
            Role.USER,
            UserStatus.ACTIVE,
        )
        val topic = Topic(
            id = 1L,
            content = "testTopic",
            visibility = true
        )


        When("BoardCreate, User, Topic 객체를 사용하여 새 Board 객체를 생성했을 때") {
            val board = Board.from(boardCreate, user, topic)

            Then("생성된 Board 객체의 속성이 boardCreate와 일치") {
                board.title shouldBe boardCreate.title
                board.content shouldBe boardCreate.content
                board.boardType shouldBe boardCreate.boardType
                board.user.id shouldBe boardCreate.userId
                board.topic.id shouldBe boardCreate.topicId
            }
        }
    }

    Given("BoardUpdate와 Board 객체가 준비되었을 때") {

        val boardUpdate = BoardUpdate(
            1L,
            "testTile",
            "testContent"
        )

        val board = Board(
            id = 1L,
            title = "testTile",
            content = "testContent",
            boardType = BoardType.BOARD_CONTENT,
            user = User(
                1L,
                "testName",
                "testEmail",
                "testPassword",
                true,
                true,
                Role.USER,
                UserStatus.ACTIVE,

                ),
            topic = Topic(
                id = 1L,
                content = "testTopic",
                visibility = true
            ),
            createdAt = null,
            updatedAt = null,
            visibility = true
        )

        When("Board 객체를 BoardUpdate 정보로 업데이트했을 때") {
            board.update(boardUpdate)

            Then("해당 보드 타이틀과 본문이 변경된다 .") {
                board.title shouldBe boardUpdate.title
                board.content shouldBe boardUpdate.content
            }
        }
    }
})
package initTest.lyfe.lyfeBe.test.comment.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.comment.CommentCreate
import lyfe.lyfeBe.comment.CommentUpdate
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import java.time.Instant

class CommentTest(
) : BehaviorSpec({

    Given("CommentCreate, User, Board 객체가 초기화되었을 때") {

        val commentCreate = CommentCreate(
            "testContent",
            1L,
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
        val board = Board(
            id = 1L,
            title = "testTitle",
            content = "testContent",
            boardType = BoardType.BOARD,
            user = user,
            topic = Topic(
                id = 1L,
                content = "testTopic",
                visibility = true
            ),
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            visibility = true
        )


        When("CommentCreate, User, Board 객체를 사용하여 새 Comment 객체를 생성했을 때") {
            val comment = Comment.from(commentCreate, user, board)

            Then("생성된 Comment 객체의 속성이 commentCreate와 일치") {
                comment.content shouldBe commentCreate.content
                comment.user.id shouldBe commentCreate.userId
                comment.board.id shouldBe commentCreate.boardId
            }
        }
    }

    Given("CommentUpdate와 Comment 객체가 준비되었을 때") {

        val commentUpdate = CommentUpdate(
            1L,
            "testContent",
            1L,
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
        val board = Board(
            id = 1L,
            title = "testTitle",
            content = "testContent",
            boardType = BoardType.BOARD,
            user = user,
            topic = Topic(
                id = 1L,
                content = "testTopic",
                visibility = true
            ),
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            visibility = true
        )
        val comment = Comment(
            id = 1L,
            content = "testContent",
            commentGroupId = null,
            user = user,
            board = board,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            visibility = true
        )

        When("CommentUpdate와 Comment 객체를 사용하여 Comment 객체를 수정했을 때") {
            val updatedComment = comment.update(commentUpdate)

            Then("수정된 Comment 객체의 속성이 CommentUpdate와 일치") {
                updatedComment.content shouldBe commentUpdate.content
            }
        }
    }
})
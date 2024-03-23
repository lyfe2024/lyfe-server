package initTest.lyfe.lyfeBe.test.board.domain

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createBoardCreate
import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createTestBoard
import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createBoardUpdate
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.web.topic.TopicFactory.Companion.createTestTopic


class BoardTest(
) : BehaviorSpec({

    val user = createTestUser()


    Given("BoardCreate, User, Topic 객체가 초기화되었을 때") {

        val boardCreate = createBoardCreate()


        val topic = createTestTopic()


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

        val boardUpdate = createBoardUpdate()

        val board = createTestBoard()

        When("Board 객체를 BoardUpdate 정보로 업데이트했을 때") {

            board.update(boardUpdate,user.id)

            Then("해당 보드 타이틀과 본문이 변경된다 .") {
                board.title shouldBe boardUpdate.title
                board.content shouldBe boardUpdate.content
            }
        }
    }
})
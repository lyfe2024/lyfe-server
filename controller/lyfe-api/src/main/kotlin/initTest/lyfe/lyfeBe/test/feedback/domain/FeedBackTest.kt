package initTest.lyfe.lyfeBe.test.feedback.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardCreate
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.BoardUpdate
import lyfe.lyfeBe.feedback.FeedBackCreate
import lyfe.lyfeBe.feedback.Feedback
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus


class FeedBackTest(
) : BehaviorSpec({

    Given("FeedBackCreate와 User 객체가 초기화되었을 때") {

        val feedBackCreate = FeedBackCreate(
            1L,
            "testFeedBack",
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
            visibility = true
        )

        When("FeedBackCreate와 User 객체를 이용하여 Feedback 객체를 생성했을 때") {
            val feedBack = Feedback.from(feedBackCreate.feedBack, user)

            Then("생성된 Feedback 객체의 속성이 FeedBackCreate의 내용과 User 객체와 일치해야 한다") {
                feedBack.content shouldBe feedBackCreate.feedBack
                feedBack.user shouldBe user
            }
        }
    }
})
package initTest.lyfe.lyfeBe.test.topic.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardCreate
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.BoardUpdate
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.topic.TopicCreate
import lyfe.lyfeBe.topic.TopicUpdate
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus


class TopicTest(
) : BehaviorSpec({

    Given("Topic 객체가 초기화되었을 때") {


        val topicCreate = TopicCreate(
            content = "testTopic",
        )

        When("TopicCreate 객체를 사용하여 새 Topic 객체를 생성했을 때") {
            val topic = Topic.from(topicCreate)

            Then("생성된 Topic 객체의 속성이 topicCreate와 일치") {
                topic.content shouldBe topicCreate.content
            }
        }
    }

    Given("TopicUpdate 객체가 준비되었을 때") {

        val topicUpdate = TopicUpdate(
            1L,
            "testTopic",
        )

            When("TopicUpdate 객체를 사용하여 Topic 객체를 업데이트 했을 때") {
                val topic = Topic.from(topicUpdate)

                Then("업데이트된 Topic 객체의 속성이 topicUpdate와 일치") {
                    topic.id shouldBe topicUpdate.topicId
                    topic.content shouldBe topicUpdate.content
                }
            }

    }
})
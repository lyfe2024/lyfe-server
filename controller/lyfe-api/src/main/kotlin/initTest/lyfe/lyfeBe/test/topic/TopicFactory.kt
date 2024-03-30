package initTest.lyfe.lyfeBe.test.topic

import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.topic.TopicCreate
import lyfe.lyfeBe.topic.TopicUpdate
import java.time.Instant
import java.time.LocalDate


class TopicFactory {

    companion object {

        fun createTesteTopic() = Topic(1L, "testTopic", Instant.now(), Instant.now(), null)


        fun createTopicUpdate(id: Long, content: String): TopicUpdate {
            return TopicUpdate(id, content, LocalDate.now())
        }

        fun createTopicCreate(content: String): TopicCreate {
            return TopicCreate(content, LocalDate.now())
        }
    }
}

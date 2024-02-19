package initTest.lyfe.lyfeBe.test.topic

import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.topic.TopicCreate
import lyfe.lyfeBe.topic.TopicUpdate


class TopicFactory {

    companion object {

        fun createTesteTopic() = Topic(1L, "testTopic")


        fun createTopicUpdate(id: Long, content: String): TopicUpdate {
            return TopicUpdate(id, content)
        }

        fun createTopicCreate(content: String): TopicCreate {
            return TopicCreate(content)
        }
    }
}

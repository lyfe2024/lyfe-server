package lyfe.lyfeBe.web.topic

import lyfe.lyfeBe.topic.Topic
import java.time.Instant

class TopicFactory {
    companion object {
        fun createTestTopic(id: Long = 1L,
                            content: String = "testTopic"): Topic {
            return Topic(
                id,
                content,
                Instant.now(),
                Instant.now()
            )
        }
    }
}
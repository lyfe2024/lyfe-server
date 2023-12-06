package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.port.out.UserPort
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class FakeTopicRepository : TopicPort {
    private val autoGeneratedId = AtomicLong(0)
    private val data: MutableList<Topic> = Collections.synchronizedList(ArrayList())


    override fun create(topic: Topic): Topic {
        val newTopic = topic.copy(id = autoGeneratedId.incrementAndGet())
        data.add(newTopic)
        return newTopic
    }

    override fun getById(topicId: Long) = data.find { it.id == topicId }!!
}
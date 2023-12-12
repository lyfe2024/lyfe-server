package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.topic.port.TopicPort
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class FakeTopicRepository : TopicPort {
    private val autoGeneratedId = AtomicLong(0)
    private val data: MutableList<Topic> = Collections.synchronizedList(ArrayList())

    override fun create(topic: Topic): Topic {
        return if (topic.id == 0L) {
            val newTopic = topic.copy(id = autoGeneratedId.incrementAndGet())
            data.add(newTopic)
            newTopic
        } else {
            data.removeIf { it.id == topic.id }
            data.add(topic)
            topic
        }
    }

    override fun getById(topicId: Long) = data.find { it.id == topicId }!!

    override fun update(from: Topic) {
        data.removeIf { it.id == from.id }
        data.add(from)
    }

    override fun getPast(date: String, pageable1: Long, pageable: Pageable): Page<Topic> {
        TODO("Not yet implemented")
    }

    fun clear() {
        data.clear()
    }
}

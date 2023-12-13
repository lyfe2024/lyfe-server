package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.fomatter.DateConverter
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.topic.port.TopicPort
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.time.Instant
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class FakeTopicRepository : TopicPort {
    private val autoGeneratedId = AtomicLong(0)
    private val data: MutableList<Topic> = Collections.synchronizedList(ArrayList())

    override fun create(topic: Topic): Topic {
        return if (topic.id == 0L) {
            val newTopic = topic.copy(id = autoGeneratedId.incrementAndGet() , appliedAt = Instant.now())
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

    override fun getPast(date: String, cursorId: Long, pageable: Pageable): Page<Topic> {
        val instantDate = DateConverter.toInstant(date)

        // data 리스트에서 필터링 및 정렬을 수행
        val filteredData = data.filter {
            it.appliedAt!! < instantDate && it.id < cursorId
        }.sortedByDescending { it.id }

        // 페이징 처리
        val pageStart = pageable.pageNumber * pageable.pageSize
        val pageEnd = (pageStart + pageable.pageSize).coerceAtMost(filteredData.size)
        val pageContent = filteredData.subList(pageStart, pageEnd)

        // Page 객체 반환
        return PageImpl(pageContent, pageable, pageable.pageSize.toLong())
    }

    fun clear() {
        data.clear()
    }
}

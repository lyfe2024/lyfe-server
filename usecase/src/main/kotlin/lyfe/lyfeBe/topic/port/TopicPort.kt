package lyfe.lyfeBe.topic.port

import lyfe.lyfeBe.topic.Topic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface TopicPort{
    fun create(topic: Topic): Topic
    fun getById(topicId: Long) : Topic
    fun update(from: Topic)
    fun getPast(date: String, cursorId: Long, pageable: Pageable): Page<Topic>


}
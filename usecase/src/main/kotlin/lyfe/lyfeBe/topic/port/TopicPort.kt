package lyfe.lyfeBe.topic.port

import lyfe.lyfeBe.topic.Topic
import java.time.LocalDate


interface TopicPort{
    fun create(topic: Topic): Topic
    fun getById(id: Long): Topic
    fun update(from: Topic): Topic
    fun getDate(date: LocalDate): Topic
    fun getToday(): Topic

}
package lyfe.lyfeBe.topic.port

import lyfe.lyfeBe.topic.Topic


interface TopicPort{
    fun getById(topicId: Long) : Topic
}
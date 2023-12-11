package lyfe.lyfeBe.topic.port

import lyfe.lyfeBe.topic.Topic


interface TopicPort{
    fun create(topic: Topic): Topic
    fun getById(topicId: Long) : Topic
    fun update(from: Topic)


}
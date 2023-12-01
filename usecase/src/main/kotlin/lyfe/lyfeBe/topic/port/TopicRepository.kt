package lyfe.lyfeBe.topic.port

import lyfe.lyfeBe.topic.Topic


interface TopicRepository {

    fun getById(topicId: Long) : Topic
}
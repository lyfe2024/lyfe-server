package lyfe.lyfeBe.persistence.topic

import lyfe.lyfeBe.topic.domain.Topic

object TopicMapper {

    fun mapToDomainEntity(topic: TopicJpaEntity): Topic =
        Topic(
            id = topic.id,
            content = topic.content,
            appliedAt = topic.appliedAt,
            createdAt = topic.createdAt,
            updatedAt = topic.updatedAt,
            visibility = topic.visibility,
        )

    fun mapToJpaEntity(topic: Topic): TopicJpaEntity =
        TopicJpaEntity(
            id = topic.id,
            content = topic.content,
            appliedAt = topic.appliedAt,
        ).apply {
            createdAt = topic.createdAt
            updatedAt = topic.updatedAt
            visibility = topic.visibility
        }
}
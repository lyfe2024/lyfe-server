package lyfe.lyfeBe.persistence.topic

import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.topic.Topic

object TopicMapper {

    fun mapToDomainEntity(topic: TopicJpaEntity): Topic =
        Topic(
            id = topic.id,
            content = topic.content,
            appliedAt = topic.appliedAt,
            createdAt = topic.baseEntity.createdAt,
            updatedAt = topic.baseEntity.updatedAt,
            visibility = topic.baseEntity.visibility,
        )

    fun mapToJpaEntity(topic: Topic): TopicJpaEntity =
        TopicJpaEntity(
            id = topic.id,
            content = topic.content,
            appliedAt = topic.appliedAt,
            baseEntity = BaseEntity(
                createdAt = topic.createdAt,
                updatedAt = topic.updatedAt,
                visibility = topic.visibility
            )
        )

}
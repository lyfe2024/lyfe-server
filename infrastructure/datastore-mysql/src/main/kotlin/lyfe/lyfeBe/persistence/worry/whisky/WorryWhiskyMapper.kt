package lyfe.lyfeBe.persistence.worry.whisky

import lyfe.lyfeBe.persistence.user.UserMapper
import lyfe.lyfeBe.persistence.worry.topic.TopicWorryMapper
import lyfe.lyfeBe.worry.domain.WorryWhisky

object WorryWhiskyMapper {

    fun mapToDomainEntity(worryWhisky: WorryWhiskyJpaEntity): WorryWhisky =
        WorryWhisky(
            id = worryWhisky.id,
            user = UserMapper.mapToDomainEntity(worryWhisky.user),
            topicWorry = TopicWorryMapper.mapToDomainEntity(worryWhisky.topicWorry),
            createdAt = worryWhisky.createdAt,
        )

    fun mapToJpaEntity(worryWhisky: WorryWhisky): WorryWhiskyJpaEntity =
        WorryWhiskyJpaEntity(
            id = worryWhisky.id,
            user = UserMapper.mapToJpaEntity(worryWhisky.user),
            topicWorry = TopicWorryMapper.mapToJpaEntity(worryWhisky.topicWorry),
            createdAt = worryWhisky.createdAt
        )
}
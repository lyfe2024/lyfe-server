package lyfe.lyfeBe.persistence.topic

import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.topic.port.TopicPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
class TopicPersistenceAdapter(
    private val topicRepository: TopicJpaRepository
) : TopicPort {

    @Transactional
    override fun create(topic: Topic): Topic{
        return topicRepository.save(TopicJpaEntity.from(topic)).toDomain()
    }
    @Transactional
    override fun update(from: Topic): Topic {
        return topicRepository.save(TopicJpaEntity.from(from)).toDomain()
    }
    override fun getById(id: Long): Topic {
        return topicRepository.findByIdOrNull(id)?.toDomain()
            ?: throw ResourceNotFoundException("주제가 존재하지 않습니다.")
    }
    override fun getDate(date: LocalDate): Topic {
        val topicJpaEntity = (topicRepository.findByAppliedAt(date)
            ?: throw ResourceNotFoundException("주제가 존재하지 않습니다."))
        return topicJpaEntity.toDomain()
    }
    override fun getToday() : Topic {
        val today = LocalDate.now()
        val topicJpaEntity = (topicRepository.findByAppliedAt(today)
            ?: throw ResourceNotFoundException("주제가 존재하지 않습니다."))
        return topicJpaEntity.toDomain()
    }
}


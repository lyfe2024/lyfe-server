package lyfe.lyfeBe.persistence.whisky

import lyfe.lyfeBe.whisky.Whisky
import lyfe.lyfeBe.whisky.out.WhiskyPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class WhiskyPersistenceAdapter(
    private val whiskyRepository: WhiskyRepository
) : WhiskyPort {
    override fun countByBoardId(boardId: Long) = whiskyRepository.countByBoardId(boardId)
    override fun create(whisky: Whisky) =
        whiskyRepository.save(WhiskyJpaEntity.from(whisky)).toDomain()
}
package lyfe.lyfeBe.persistence.whisky

import lyfe.lyfeBe.whisky.Whisky
import lyfe.lyfeBe.whisky.out.WhiskyPort
import org.springframework.stereotype.Component

@Component
class WhiskyPersistenceAdapter(
    private val whiskyRepository: WhiskyRepository
) : WhiskyPort {

    override fun existByBoardIdAndUserId(boardId: Long, userId: Long): Boolean {
        return whiskyRepository.existsByBoardIdAndUserId(boardId, userId)
    }

    override fun deleteByBoardIdAndUserId(boardId: Long, userId: Long) {
        whiskyRepository.deleteByBoardIdAndUserId(boardId, userId)
    }

    override fun create(whisky: Whisky): Whisky {
        return whiskyRepository.save(WhiskyJpaEntity.from(whisky)).toDomain()
    }

    override fun countByBoardId(boardId: Long): Int {
        return whiskyRepository.countByBoardId(boardId)
    }
}
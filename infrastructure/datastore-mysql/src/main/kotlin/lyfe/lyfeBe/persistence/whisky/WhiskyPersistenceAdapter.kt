package lyfe.lyfeBe.persistence.whisky

import lyfe.lyfeBe.whisky.Whisky
import lyfe.lyfeBe.whisky.out.WhiskyPort
import org.springframework.stereotype.Component

@Component
class WhiskyPersistenceAdapter(
    private val whiskyRepository: WhiskyRepository
) : WhiskyPort {
    override fun countByBoardId(boardId: Long) = whiskyRepository.countByBoardId(boardId)
    override fun create(whisky: Whisky) =
        whiskyRepository.save(WhiskyJpaEntity.from(whisky)).toDomain()

    override fun update(boardId: Long) {

        whiskyRepository
    }

    override fun assertNoExistingWhisky(boardId: Long, userId: Long) {
        whiskyRepository.findByBoardIdAndUserId(boardId, userId)?.let {
            throw IllegalStateException("이미 좋아요를 누른 게시글입니다.")
        }
    }

    override fun delete(boardId: Long, userId: Long) {
        whiskyRepository.deleteByBoardIdAndUserId(boardId, userId)
    }

    override fun get(whiskyId: Long) = whiskyRepository.findById(whiskyId).get().toDomain()
}
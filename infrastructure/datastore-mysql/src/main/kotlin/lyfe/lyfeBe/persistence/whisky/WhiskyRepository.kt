package lyfe.lyfeBe.persistence.whisky

import org.springframework.data.jpa.repository.JpaRepository

interface WhiskyRepository: JpaRepository<WhiskyJpaEntity, Long> {

    fun existsByBoardIdAndUserId(boardId: Long, userId: Long): Boolean
    fun deleteByBoardIdAndUserId(boardId: Long, userId: Long)
    fun countByBoardId(boardId: Long): Int
}
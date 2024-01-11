package lyfe.lyfeBe.persistence.whisky

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface WhiskyRepository: JpaRepository<WhiskyJpaEntity, Long> {

    @Query("SELECT COUNT(w) FROM WhiskyJpaEntity w WHERE w.board.id = :boardId")
    fun countByBoardId(@Param("boardId") boardId: Long): Int
    fun findByBoardIdAndUserId(boardId: Long, userId: Long): WhiskyJpaEntity?
    fun deleteByBoardIdAndUserId(boardId: Long, userId: Long)
}
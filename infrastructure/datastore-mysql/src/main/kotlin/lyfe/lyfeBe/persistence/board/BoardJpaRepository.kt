package lyfe.lyfeBe.persistence.board

import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.persistence.topic.TopicJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface BoardJpaRepository: JpaRepository<BoardJpaEntity, Long> {

    @Query("SELECT t FROM BoardJpaEntity t WHERE t.id < :cursorId")
    fun findByIdCursorId(cursorId: Long, pageable: Pageable): Page<BoardJpaEntity>

    @Query("SELECT t FROM BoardJpaEntity t WHERE t.user.id = :userId AND t.boardType = :boardType")
    fun findByUserIdAndBoardType(userId: Long, boardType: BoardType, pageable: Pageable): Page<BoardJpaEntity>
}
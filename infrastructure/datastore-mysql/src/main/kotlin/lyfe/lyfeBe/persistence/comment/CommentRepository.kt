package lyfe.lyfeBe.persistence.comment

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CommentRepository: JpaRepository<CommentJpaEntity, Long> {
    fun findAllByUserIdAndIdLessThanOrderByIdDesc(userId: Long,  cursorId: Long ): List<CommentJpaEntity>
    fun findAllByBoardIdAndIdLessThanOrderByIdDesc(boardId: Long, cursorId: Long, pageable: Pageable): List<CommentJpaEntity>
    fun findFirstByBoardIdOrderByIdDesc(boardId: Long): CommentJpaEntity?

    @Query("SELECT COUNT(c) FROM CommentJpaEntity c WHERE c.board.id = :boardId")
    fun countByBoardId(@Param("boardId") boardId: Long): Int
}
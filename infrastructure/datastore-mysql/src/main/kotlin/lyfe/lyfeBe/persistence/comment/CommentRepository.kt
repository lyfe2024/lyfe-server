package lyfe.lyfeBe.persistence.comment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CommentRepository: JpaRepository<CommentJpaEntity, Long> {
    fun findAllByUserIdAndIdLessThanOrderByIdDesc(cursorId: Long, userId: Long): List<CommentJpaEntity>
    fun findAllByBoardIdAndIdLessThanOrderByIdDesc(cursorId: Long, boardId: Long, map: Any?): List<CommentJpaEntity>
    fun findFirstByBoardIdOrderByIdDesc(boardId: Long): CommentJpaEntity?

    @Query("SELECT COUNT(c) FROM CommentJpaEntity c WHERE c.board.id = :boardId")
    fun countByBoardId(@Param("boardId") boardId: Long): Int
}
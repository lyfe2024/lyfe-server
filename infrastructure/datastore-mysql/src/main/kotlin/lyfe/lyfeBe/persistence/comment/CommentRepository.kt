package lyfe.lyfeBe.persistence.comment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CommentRepository: JpaRepository<CommentJpaEntity, Long> {

    @Query("SELECT COUNT(c) FROM CommentJpaEntity c WHERE c.board.id = :boardId")
    fun countByBoardId(@Param("boardId") boardId: Long): Int
}
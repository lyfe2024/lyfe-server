package lyfe.lyfeBe.persistence.board

import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository

interface BoardJpaRepository: JpaRepository<BoardJpaEntity, Long> {
    fun findByIdLessThanOrderByIdDesc(boardId: Long, pageable: PageRequest) : List<BoardJpaEntity>
}
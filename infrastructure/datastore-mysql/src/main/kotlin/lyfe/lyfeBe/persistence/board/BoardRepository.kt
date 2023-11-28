package lyfe.lyfeBe.persistence.board

import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository: JpaRepository<BoardJpaEntity, Long> {
}
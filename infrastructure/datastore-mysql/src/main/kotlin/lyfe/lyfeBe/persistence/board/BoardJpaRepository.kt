package lyfe.lyfeBe.persistence.board

import org.springframework.data.jpa.repository.JpaRepository

interface BoardJpaRepository: JpaRepository<BoardJpaEntity, Long> {
}
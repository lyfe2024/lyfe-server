package lyfe.lyfeBe.persistence.notification

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NotificationHistoryRepository: JpaRepository<NotificationHistoryJpaEntity, Long> {

    @Query("SELECT n FROM NotificationHistoryJpaEntity n WHERE n.id < :cursorId")
    fun findByIdCursorId(cursorId: Long, paging: Pageable): Page<NotificationHistoryJpaEntity>
}
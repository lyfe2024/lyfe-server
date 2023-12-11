package lyfe.lyfeBe.persistence.notification

import org.springframework.data.jpa.repository.JpaRepository

interface NotificationHistoryRepository: JpaRepository<NotificationHistoryJpaEntity, Long> {
}
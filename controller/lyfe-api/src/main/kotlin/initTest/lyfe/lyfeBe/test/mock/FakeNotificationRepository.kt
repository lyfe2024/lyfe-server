package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.fcm.port.FcmPort
import lyfe.lyfeBe.notification.NotificationHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class FakeNotificationRepository : FcmPort {

    private val autoGeneratedId = AtomicLong(0)
    private val data: MutableList<NotificationHistory> = Collections.synchronizedList(ArrayList())

    override fun save(notificationHistory: NotificationHistory): NotificationHistory {
        return if (notificationHistory.id == 0L) {
            val newNotificationHistory = notificationHistory.copy(id = autoGeneratedId.incrementAndGet())
            data.add(newNotificationHistory)
            newNotificationHistory
        } else {
            data.removeIf { it.id == notificationHistory.id }
            data.add(notificationHistory)
            notificationHistory
        }
    }


    override fun findByIdCursorId(cursorId: Long, paging: Pageable): Page<NotificationHistory> {

        // data 리스트에서 필터링 및 정렬을 수행
        val filteredData = data.filter {
            it.id < cursorId
        }.sortedByDescending { it.id }

        // 페이징 처리
        val pageStart = paging.pageNumber * paging.pageSize
        val pageEnd = (pageStart + paging.pageSize).coerceAtMost(filteredData.size)
        val pageContent = filteredData.subList(pageStart, pageEnd)

        return PageImpl(pageContent, paging, paging.pageSize.toLong())

    }


    fun clear() {
        data.clear()
    }
}
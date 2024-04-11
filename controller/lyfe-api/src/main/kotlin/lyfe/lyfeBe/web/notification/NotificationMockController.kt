package lyfe.lyfeBe.web.notification

import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.notification.NotificationType
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class NotificationMockController {
    @GetMapping("/v1/notifications")
    fun getNotificationList(
        @RequestParam(required = false) cursorId: Long?,
        @PageableDefault(size = 5, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): CommonResponse<NotificationListResponse> {
        val notificationList = generateList(10) { i ->
            val notificationType = when ((i % 4) + 1) {
                1 -> NotificationType.BOARD_PICTURE
                2 -> NotificationType.BOARD
                3 -> NotificationType.COMMENT
                4 -> NotificationType.WHISKY
                else -> throw IllegalArgumentException("Invalid id")
            }

            NotificationResponse(
                id = i.toLong(),
                notificationType = notificationType,
                notificationTargetId = i.toLong(),
                content = "알림$i",
                notifiedAt = Instant.now().toString()
            )
        }

        return CommonResponse(NotificationListResponse(notificationList))
    }

    private inline fun <reified T> generateList(size: Int, createFn: (Int) -> T): List<T> {
        return (1..size).map { createFn(it) }
    }
}
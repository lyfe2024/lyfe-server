package lyfe.lyfeBe.web.notification

import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.dto.PageInfo
import lyfe.lyfeBe.notification.NotificationType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NotificationMockController {
    @GetMapping("/v1/notifications")
    fun getNotificationList(): CommonResponse<NotificationListResponse> {
        val notificationList = generateList(10) { i ->
            val notificationType = when ((i % 4) + 1) {
                1 -> NotificationType.BOARD_PICTURE
                2 -> NotificationType.BOARD_CONTENT
                3 -> NotificationType.COMMENT
                4 -> NotificationType.WHISKY
                else -> throw IllegalArgumentException("Invalid id")
            }

            NotificationResponse(
                notificationType = notificationType,
                content = "알림$i",
                notifiedAt = "2021-01-01"
            )
        }

        return CommonResponse(
            result = NotificationListResponse(notificationList),
            page = PageInfo(
                size = 10,
                number = 1,
                totalElements = 10,
                totalPages = 1
            )
        )
    }

    private inline fun <reified T> generateList(size: Int, createFn: (Int) -> T): List<T> {
        return (1..size).map { createFn(it) }
    }
}
package lyfe.lyfeBe.fcm

import FcmMessageDto
import lyfe.lyfeBe.fcm.req.NotificationRequest
import lyfe.lyfeBe.notification.NotificationGet
import lyfe.lyfeBe.notification.NotificationSend
import lyfe.lyfeBe.utils.ControllerUtils.Companion.getEffectiveCursorId
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/notification")
class NotificationController(private val fcmService: FCMService) {

    @PostMapping("/send")
    fun sendNotification(@RequestBody request: NotificationRequest): String {
        return fcmService.sendMessage(
            NotificationSend(
                userId = request.userId,
                token = request.token,
                type = request.notificationType,
                content = request.notificationContent
            )

        )
    }

    @GetMapping
    fun getNotificationHistories(
        @RequestParam(required = false) cursorId: Long?,
        @PageableDefault(size = 10, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): List<FcmMessageDto> {
        val notificationId = getEffectiveCursorId(cursorId)
         return fcmService.getNotificationHistories(NotificationGet(notificationId , pageable))
    }
}


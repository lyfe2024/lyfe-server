package lyfe.lyfeBe.fcm

import com.google.firebase.messaging.Message
import lyfe.lyfeBe.fcm.req.NotificationRequest
import lyfe.lyfeBe.notification.NotificationSend
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

    @GetMapping("/list")
    fun listMessages(): List<Message> {
        return fcmService.listMessages()
    }
}


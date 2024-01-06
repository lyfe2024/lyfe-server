package lyfe.lyfeBe.fcm

import com.google.firebase.messaging.Message
import lyfe.lyfeBe.fcm.req.NotificationRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/notification")
class NotificationController(private val fcmService: FCMService) {

    @PostMapping("/send")
    fun sendNotification(@RequestBody request: NotificationRequest): String {
        return fcmService.sendMessage(request.token, request.notificationType, request.notificationContent)
    }

    // 메시지 조회 엔드포인트
    @GetMapping("/list")
    fun listMessages(): List<Message> {
        return fcmService.listMessages()
    }
}


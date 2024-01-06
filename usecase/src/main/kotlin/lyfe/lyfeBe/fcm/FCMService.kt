package lyfe.lyfeBe.fcm

import com.google.firebase.messaging.*
import lyfe.lyfeBe.notification.NotificationContent
import lyfe.lyfeBe.notification.NotificationType
import org.springframework.stereotype.Service

@Service
class FCMService {

    fun sendMessage(token: String, title: NotificationType, body: NotificationContent): String {

        val message = Message.builder()
            .setToken(token)
            .setAndroidConfig(FcmNotificationBuilder.androidConfig(title, body))
            .setApnsConfig(FcmNotificationBuilder.iosConfig(title, body))
            .build()
        return FirebaseMessaging.getInstance().send(message)
    }

    fun listMessages(): List<Message> {
        // TODO: 데이터베이스 또는 다른 저장소에서 메시지 목록 조회 로직 구현
        return listOf()
    }
}
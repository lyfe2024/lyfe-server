package lyfe.lyfeBe.fcm

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import lyfe.lyfeBe.notification.NotificationSend
import org.springframework.stereotype.Service

@Service
class FCMService {

    fun sendMessage(send: NotificationSend): String {

        val message = Message.builder()
            .setToken(send.token)
            .setAndroidConfig(FcmNotificationBuilder.androidConfig(send.type, send.content))
            .setApnsConfig(FcmNotificationBuilder.iosConfig(send.type, send.content))
            .build()
        return FirebaseMessaging.getInstance().send(message)
    }

    fun listMessages(): List<Message> {
        // TODO: 데이터베이스 또는 다른 저장소에서 메시지 목록 조회 로직 구현
        return listOf()
    }
}
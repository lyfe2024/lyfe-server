package lyfe.lyfeBe.fcm

import org.springframework.stereotype.Service
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification

@Service
class FCMService {

    fun sendMessage(topic: String, title: String, body: String): String {
        val message = Message.builder()
            .setTopic(topic)
            .setNotification(Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build())
            .build()

        return FirebaseMessaging.getInstance().send(message)
    }

    // FCM 메시지 조회는 FCM에서 직접적으로 제공하지 않습니다.
    // 이 기능은 일반적으로 사용자가 서버에 메시지 정보를 저장하여 구현합니다.
    // 아래의 예시 메소드는 이러한 기능을 구현하기 위한 뼈대를 제공합니다.
    fun listMessages(): List<Message> {
        // TODO: 데이터베이스 또는 다른 저장소에서 메시지 목록 조회 로직 구현
        return listOf()
    }
}
package lyfe.lyfeBe.fcm

import FcmMessageDto
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.transaction.annotation.Transactional
import lyfe.lyfeBe.fcm.port.FcmPort
import lyfe.lyfeBe.notification.NotificationGet
import lyfe.lyfeBe.notification.NotificationHistory
import lyfe.lyfeBe.notification.NotificationSend
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.stereotype.Service

@Service
class FCMService(
    private val fcmPort: FcmPort,
    private val userPort: UserPort


    ) {

    @Transactional
    fun sendMessage(send: NotificationSend): String {
        val message = Message.builder()
            .setToken(send.token)
            .setAndroidConfig(FcmNotificationBuilder.androidConfig(send.type, send.content))
            .setApnsConfig(FcmNotificationBuilder.iosConfig(send.type, send.content))
            .build()

        FirebaseMessaging.getInstance().send(message)

        val user = userPort.getById(send.userId)
        return fcmPort.save(NotificationHistory.from(send,user)).id.toString()
    }

    @Transactional(readOnly = true)
    fun getNotificationHistories(notificationGet: NotificationGet): List<FcmMessageDto> {
        val notificationHistories = fcmPort.findByIdCursorId(notificationGet.notificationId, notificationGet.pageable).toList()
        return FcmMessageDto.toDtos(notificationHistories)
    }
}
package lyfe.lyfeBe.fcm

import com.google.firebase.messaging.*
import lyfe.lyfeBe.notification.NotificationContent
import lyfe.lyfeBe.notification.NotificationType

class FcmNotificationBuilder {
    companion object {
        fun iosConfig(type: NotificationType, content: NotificationContent): ApnsConfig =
            ApnsConfig.builder()
                .setAps(
                    Aps.builder()
                        .setAlert(
                            ApsAlert.builder()
                                .setTitle(type.name)
                                .setBody(content.description)
                                .build()
                        )
                        .build()
                )
                .build()

        fun androidConfig(type: NotificationType, content: NotificationContent): AndroidConfig =
            AndroidConfig.builder()
                .setTtl(3600 * 1000) // 1 hour in milliseconds
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(
                    AndroidNotification.builder()
                        .setTitle(type.name)
                        .setBody(content.description)
                        .build()
                )
                .build()
    }
}

package lyfe.lyfeBe.fcm.req

data class NotificationRequest(
    val topic: String,
    val title: String,
    val body: String
)

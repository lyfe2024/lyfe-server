package lyfe.lyfeBe.notification

enum class NotificationContent(val description: String) {
    PICTURE_REWARDED("신청하신 사진이 위스키 잔을 받았어요!"),
    PICTURE_COMMENTED("신청하신 사진에 댓글이 달렸어요. 확인하러 가볼까요?"),
    BOARD_REWARDED("작성하신 글이 위스키 잔을 받았어요."),
    BOARDCOMMENTED("작성하신 글에 댓글이 달렸어요. 확인하러 가볼까요?")
}
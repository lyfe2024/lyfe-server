package lyfe.lyfeBe.user

data class UserUpdate(
    val nickname: String,
    val profileUrl: String,
    val width: Int,
    val height: Int
)

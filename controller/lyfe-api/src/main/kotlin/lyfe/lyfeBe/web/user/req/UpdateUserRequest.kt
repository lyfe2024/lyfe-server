package lyfe.lyfeBe.web.user.req

data class UpdateUserRequest(
    val nickname: String,
    val profileUrl: String,
    val width: Int,
    val height: Int
)

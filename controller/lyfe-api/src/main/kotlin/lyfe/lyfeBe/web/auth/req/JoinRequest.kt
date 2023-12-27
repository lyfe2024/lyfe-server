package lyfe.lyfeBe.web.auth.req

data class JoinRequest(
    val userToken: String,
    val nickname: String,
)

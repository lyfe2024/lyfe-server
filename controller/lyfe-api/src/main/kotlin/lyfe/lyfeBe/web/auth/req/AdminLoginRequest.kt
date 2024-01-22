package lyfe.lyfeBe.web.auth.req

data class AdminLoginRequest(
    val email: String,
    val password: String
)

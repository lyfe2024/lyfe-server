package lyfe.lyfeBe.web.auth

import lyfe.lyfeBe.auth.AuthLogin
import lyfe.lyfeBe.auth.dto.LoginDto
import lyfe.lyfeBe.auth.service.AuthService
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.user.UserJoin
import lyfe.lyfeBe.web.auth.req.AdminLoginRequest
import lyfe.lyfeBe.web.auth.req.AuthRequest
import lyfe.lyfeBe.web.auth.req.JoinRequest
import lyfe.lyfeBe.web.auth.req.RefreshTokenRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val service: AuthService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody req: AuthRequest
    ) = CommonResponse(
        service.loginAccess(
            AuthLogin(
                socialType = req.socialType,
                authorizationCode = req.authorizationCode,
                idToken = req.identityToken,
                fcmToken = req.fcmToken
            )
        )
    )

    @PostMapping("/join")
    fun join(
        @RequestBody req: JoinRequest
    ) = CommonResponse(
        service.join(
            UserJoin(
                userToken = req.userToken,
                nickname = req.nickname
            )
        )
    )

    @PostMapping("/reissue")
    fun reIssueToken(
        @RequestBody req: RefreshTokenRequest
    ) = CommonResponse(
        service.reIssueToken(
            req.token
        )
    )

    @PostMapping("/admin")
    fun admin(
        @RequestBody req: AdminLoginRequest
    ) = CommonResponse(
        service.adminLogin(
            LoginDto(
                email = req.email,
                password = req.password
            )
        )
    )

    @PostMapping("/revoke")
    fun revoke(
    ) = CommonResponse(
        service.revoke()
    )
}
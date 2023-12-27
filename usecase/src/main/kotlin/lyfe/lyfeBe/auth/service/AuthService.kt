package lyfe.lyfeBe.auth.service

import lyfe.lyfeBe.auth.*
import lyfe.lyfeBe.auth.JwtTokenValidator.verifyToken
import lyfe.lyfeBe.auth.dto.*
import lyfe.lyfeBe.auth.dto.apple.AppleLoginRequest
import lyfe.lyfeBe.auth.dto.google.GoogleLoginRequest
import lyfe.lyfeBe.auth.dto.kakao.KakaoLoginRequest
import lyfe.lyfeBe.auth.port.out.RefreshTokenPort
import lyfe.lyfeBe.auth.service.apple.AppleService
import lyfe.lyfeBe.auth.service.google.GoogleService
import lyfe.lyfeBe.auth.service.kakao.KakaoService
import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.error.UnauthenticatedException
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserCreate
import lyfe.lyfeBe.user.UserJoin
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userPort: UserPort,
    private val refreshTokenPort: RefreshTokenPort,
    private val jwtTokenProvider: JwtTokenProvider,
    private val kakaoService: KakaoService,
    private val googleService: GoogleService,
    private val appleService: AppleService
) {
    companion object {
        const val EMAIL_CLAIM: String = "email"
    }
    fun loginAccess(authLogin: AuthLogin): Any {
        val socialIdAndRefreshToken = fetchSocialEmail(
            socialType = authLogin.socialType,
            authorizationCode = authLogin.authorizationCode,
            identityToken = authLogin.identityToken
        )
        val socialEmail = "${socialIdAndRefreshToken.oAuthId}@${authLogin.socialType.name}"
        val user = userPort.getByEmail(socialEmail)
        if (user == null) {
            return createUserIfNotExists(socialIdAndRefreshToken, socialEmail, authLogin.socialType)
        }else if(user.role == Role.GUEST){
            return JoinDto(userToken = jwtTokenProvider.createTokenForOAuth2(socialEmail))
        }

        val loginUser = login(LoginDto.toDto(user))

        return TokenDto(
            accessToken = loginUser.accessToken,
            refreshToken = loginUser.refreshToken
        )
    }

    fun join(userJoin: UserJoin): TokenDto {
        val claims = verifyToken(userJoin.userToken)
        val email = claims[EMAIL_CLAIM] as String
        val user = userPort.getByEmail(email)?.updateNickName(userJoin.nickname)
            ?: throw ResourceNotFoundException("회원가입이 필요합니다.")
        return login(LoginDto.toDto(user))
    }

    fun login(loginDto: LoginDto): TokenDto {
        val authentication = loginDto.toAuthentication()
        val principal = authentication.principal as PrincipalDetails

        val generateToken = jwtTokenProvider.generateToken(authentication = loginDto.toAuthentication())
        val user = userPort.findByEmail(principal.username)
            ?: throw ResourceNotFoundException("회원가입이 필요합니다.")

        val refreshToken = RefreshToken.from(RefreshTokenCreate(generateToken.refreshToken), user)
        refreshTokenPort.create(refreshToken)

        return TokenDto(
            accessToken = generateToken.accessToken,
            refreshToken = generateToken.refreshToken
        )
    }

    private fun createUserIfNotExists(
        oAuthIdAndRefreshTokenDto: OAuthIdAndRefreshTokenDto,
        socialEmail: String,
        socialType: SocialType
    ): JoinDto {
        val userCreate = UserCreate(
            email = socialEmail,
            nickname = "",
            socialId = oAuthIdAndRefreshTokenDto.oAuthId,
            socialType = socialType,
            socialRefreshToken = oAuthIdAndRefreshTokenDto.refreshToken,
            role = Role.GUEST
        )

        userPort.create(User.from(userCreate))
        return JoinDto(userToken = jwtTokenProvider.createTokenForOAuth2(oAuthIdAndRefreshTokenDto.oAuthId))
    }

    fun fetchSocialEmail(
        socialType: SocialType,
        authorizationCode: String,
        identityToken: String?,
    ): OAuthIdAndRefreshTokenDto {
        return when (socialType) {
            SocialType.KAKAO -> kakaoService.fetchKakaoToken(KakaoLoginRequest(authorizationCode))
            SocialType.GOOGLE -> googleService.fetchGoogleToken(GoogleLoginRequest(authorizationCode))
            SocialType.APPLE -> appleService.fetchAppleToken(AppleLoginRequest(authorizationCode, identityToken!!))
        }
    }

    fun reIssueToken(token : String): TokenDto {
        val authentication = jwtTokenProvider.getAuthentication(refreshToken = token)
            ?: throw ResourceNotFoundException("로그인이 필요합니다.")

        val principal = authentication.principal as PrincipalDetails
        val generateToken = jwtTokenProvider.generateToken(authentication = authentication)

        val user = userPort.findByEmail(principal.username)
            ?: throw ResourceNotFoundException("회원가입이 필요합니다.")

        val refreshToken = RefreshToken.from(RefreshTokenCreate(generateToken.refreshToken), user)
        refreshTokenPort.create(refreshToken)

        return TokenDto(
            accessToken = generateToken.accessToken,
            refreshToken = generateToken.refreshToken
        )
    }
}
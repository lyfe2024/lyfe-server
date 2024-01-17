package lyfe.lyfeBe.auth.service

import lyfe.lyfeBe.auth.AuthLogin
import lyfe.lyfeBe.auth.RefreshToken
import lyfe.lyfeBe.auth.RefreshTokenCreate
import lyfe.lyfeBe.auth.dto.JoinDto
import lyfe.lyfeBe.auth.dto.LoginDto
import lyfe.lyfeBe.auth.dto.OAuthIdAndRefreshTokenDto
import lyfe.lyfeBe.auth.dto.TokenDto
import lyfe.lyfeBe.auth.port.out.RefreshTokenPort
import lyfe.lyfeBe.auth.service.JwtTokenInfo.EMAIL_CLAIM
import lyfe.lyfeBe.error.ResourceNotFoundException
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserJoin
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val userPort: UserPort,
    private val refreshTokenPort: RefreshTokenPort,
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtTokenValidator: JwtTokenValidator,
    private val authProviderServices: List<AuthProviderService>,
    private val authenticationService: AuthenticationService,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder
) {

    fun loginAccess(authLogin: AuthLogin): Any {
        val socialIdAndRefreshToken = fetchSocialEmail(authLogin)
        val socialEmail = "${socialIdAndRefreshToken.oAuthId}@${authLogin.socialType.name}"
        val user = userPort.getByEmail(socialEmail)
            ?: return JoinDto(jwtTokenProvider.createTokenForOAuth2(socialEmail, socialIdAndRefreshToken.refreshToken))
        return login(LoginDto.fromUser(user))
    }

    fun join(userJoin: UserJoin): TokenDto {
        val claims = jwtTokenValidator.verifyToken(userJoin.userToken)
        val email = claims[EMAIL_CLAIM] as String

        val user = User.from(userJoin, email, passwordEncoder.encode(email+"lyfe"))
        userPort.create(user)

        return login(LoginDto.fromUser(user))
    }

    fun login(loginDto: LoginDto): TokenDto {
        val authentication = authenticationManager.authenticate(loginDto.toAuthentication())
        val generateToken = jwtTokenProvider.generateToken(authentication = authentication)
        val user = getUser(authentication.name)

        saveOrUpdateRefreshToken(generateToken.refreshToken, user)

        return TokenDto(
            accessToken = generateToken.accessToken,
            refreshToken = generateToken.refreshToken
        )
    }

    fun adminLogin(loginDto: LoginDto): TokenDto {
        val authentication = authenticationManager.authenticate(loginDto.toAuthentication())
        val generateToken = jwtTokenProvider.generateToken(authentication = authentication)
        val user = getUser(authentication.name)
        if(user.role != Role.ADMIN) throw ResourceNotFoundException("관리자만 접근 가능합니다.")

        saveOrUpdateRefreshToken(generateToken.refreshToken, user)

        return TokenDto(
            accessToken = generateToken.accessToken,
            refreshToken = generateToken.refreshToken
        )
    }

    fun reIssueToken(token: String): TokenDto {
        val authentication = authenticationService.getAuthentication(token = token)
        val generateToken = jwtTokenProvider.generateToken(authentication = authentication)
        val user = getUser(authentication.name)

        val refreshToken = RefreshToken.from(
            RefreshTokenCreate(
                userId = user.id,
                refreshToken = generateToken.refreshToken,
                user = user
            )
        )
        refreshTokenPort.create(refreshToken)

        return TokenDto(
            accessToken = generateToken.accessToken,
            refreshToken = generateToken.refreshToken
        )
    }

    fun saveOrUpdateRefreshToken(token: String, user: User) {
        val findByUserToken = refreshTokenPort.findByUser(user)
        val refreshToken = RefreshToken.from(
            RefreshTokenCreate(
                userId = user.id,
                refreshToken = token,
                user = user
            )
        )
        if (findByUserToken == null) { refreshTokenPort.create(refreshToken) }
        else refreshTokenPort.update(refreshToken)
    }

    fun fetchSocialEmail(authLogin: AuthLogin): OAuthIdAndRefreshTokenDto {
        val authProviderService = authProviderServices
            .find { it isSupport(authLogin.socialType) }
            ?: throw ResourceNotFoundException("지원하지 않는 소셜 로그인입니다.")
        return authProviderService.fetchAuthToken(authLogin)
    }

    private fun getUser(email: String): User {
        return userPort.getByEmail(email)
            ?: throw ResourceNotFoundException("회원가입이 필요합니다.")
    }
}
package lyfe.lyfeBe.config.security

import lyfe.lyfeBe.auth.service.PrincipalDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val principalDetailService : PrincipalDetailService,
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(): AuthenticationManager? {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(principalDetailService)
        return ProviderManager(provider)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { httpBasic -> httpBasic.disable() }
            .csrf { csrf -> csrf.disable() }
            .cors { }
            .formLogin { formLogin -> formLogin.disable() }
            .sessionManagement { sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
            }
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers(HttpMethod.GET, "/v1/boards/**").permitAll()
                    .requestMatchers(
                        "/health", "/",
                        "/v1/images/**",
                        "/v1/auth/**",
                        "/v1/users/check-nickname/**",
                        "/v1/topics/**",
                        "/v1/comments/latest",
                        "/v1/policys/**",
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}


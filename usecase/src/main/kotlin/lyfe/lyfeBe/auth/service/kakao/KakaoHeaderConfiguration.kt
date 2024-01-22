package lyfe.lyfeBe.auth.service.kakao

import feign.Logger
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Bean


class KakaoHeaderConfiguration {
    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor { template: RequestTemplate ->
            template.header(
                "Content-Type",
                "application/json;charset=UTF-8"
            )
        }
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }
}
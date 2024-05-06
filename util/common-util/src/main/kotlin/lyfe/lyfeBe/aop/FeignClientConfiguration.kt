package lyfe.lyfeBe.aop

import feign.codec.ErrorDecoder
import lyfe.lyfeBe.error.CustomErrorDecoder
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

@Configuration
class FeignClientConfiguration {
    @Bean
    fun errorDecoder(): ErrorDecoder {
        return CustomErrorDecoder()
    }

    @Bean
    fun feignMessageConverter(): MappingJackson2HttpMessageConverter {
        return MappingJackson2HttpMessageConverter()
    }

    @Bean
    fun restTemplate(messageConverters: List<HttpMessageConverter<*>>): RestTemplate {
        return RestTemplateBuilder().additionalMessageConverters(messageConverters).build()
    }
}

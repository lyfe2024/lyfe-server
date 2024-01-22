package lyfe.lyfeBe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication(scanBasePackages = ["lyfe.lyfeBe"])
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
package lyfe.lyfeBe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication(scanBasePackages = ["lyfe.lyfeBe"])
@EnableJpaAuditing
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
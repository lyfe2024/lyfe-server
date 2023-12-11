package lyfe.lyfeBe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = ["lyfe.lyfeBe"])
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
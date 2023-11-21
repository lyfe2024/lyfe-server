package lyfe.lyfeBe.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController(
    @Value("\${spring.profiles.active:}") private val profile: String = "unknown"
) {

    @GetMapping("/health", "/")
    fun healthCheck(): String = profile

}

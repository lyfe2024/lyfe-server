package lyfe.lyfeBe.persistence.worry.whisky

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class WorryWhiskyPersistenceAdapter(
    private val worryWhiskyRepository: lyfe.lyfeBe.persistence.worry.whisky.WorryWhiskyRepository
){
}
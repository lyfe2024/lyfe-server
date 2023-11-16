package lyfe.lyfeBe.persistence.policy

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class PolicyPersistenceAdapter(
    private val policyRepository: lyfe.lyfeBe.persistence.policy.PolicyRepository
) {
}
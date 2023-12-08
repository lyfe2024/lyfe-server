package lyfe.lyfeBe.persistence.policy

import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.policy.out.PolicyPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class PolicyPersistenceAdapter(
    private val policyRepository: PolicyRepository
): PolicyPort {
    override fun getPolicy(type: PolicyType) = policyRepository.findByPolicyType(type).toDomain()
}

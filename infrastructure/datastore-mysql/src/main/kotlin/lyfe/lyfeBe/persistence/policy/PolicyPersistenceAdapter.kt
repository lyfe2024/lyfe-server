package lyfe.lyfeBe.persistence.policy

import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.policy.out.PolicyPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PolicyPersistenceAdapter(
    private val policyRepository: PolicyRepository
) : PolicyPort {
    override fun getPolicy(type: PolicyType) = policyRepository.findByPolicyType(type).toDomain()

    override fun create(policy: Policy) = policyRepository.save(PolicyJpaEntity.from(policy)).toDomain()

    override fun findById(id: Long) = policyRepository.findById(id).orElseThrow().toDomain()
    override fun update(policy: Policy): Policy {
        val updatePolicy = PolicyJpaEntity.update(policy)
        return policyRepository.save(updatePolicy).toDomain()
    }
}

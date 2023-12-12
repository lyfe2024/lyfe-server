package lyfe.lyfeBe.policy

import lyfe.lyfeBe.policy.dto.PolicyDto
import lyfe.lyfeBe.policy.out.PolicyPort

import org.springframework.stereotype.Service

@Service
class PolicyService(
    private val policyPort: PolicyPort
) {
    fun getPolicy(type: PolicyType) = PolicyDto.toDto(policyPort.getPolicy(type))

    fun create(policyCreate: PolicyCreate): Policy {
        val policy = Policy.from(policyCreate)
        return policyPort.create(policy)
    }

    fun update(policyUpdate: PolicyUpdate): Policy {
        val policy = getById(policyUpdate.id).update(policyUpdate)
        return policyPort.update(policy)
    }

    private fun getById(id: Long) = policyPort.findById(id)
}

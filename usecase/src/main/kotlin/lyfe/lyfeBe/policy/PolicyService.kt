package lyfe.lyfeBe.policy

import lyfe.lyfeBe.policy.dto.PolicyDto
import lyfe.lyfeBe.policy.out.PolicyPort

import org.springframework.stereotype.Service

@Service
class PolicyService(
    private val policyPort: PolicyPort
) {
    fun getPolicy(type: PolicyType) = PolicyDto.toDto(policyPort.getPolicy(type))
}

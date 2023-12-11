package lyfe.lyfeBe.policy.dto

import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyType

data class PolicyDto(
        val title : String,
        val content : String,
        val version : String,
        val policyType : PolicyType
) {
    companion object {
        fun toDto(policy: Policy): PolicyDto {
            return PolicyDto(
                    title = policy.title,
                    content = policy.content,
                    version = policy.version,
                    policyType = policy.policyType
            )
        }
    }
}
package lyfe.lyfeBe.policy.out

import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyType

interface PolicyPort {
    fun getPolicy(type: PolicyType): Policy
    fun create(policy: Policy): Policy
    fun findById(id: Long): Policy
    fun update(policy: Policy): Policy
}
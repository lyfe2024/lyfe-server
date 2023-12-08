package lyfe.lyfeBe.policy.out

import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyType

interface PolicyPort {
    fun getPolicy(type: PolicyType): Policy
}
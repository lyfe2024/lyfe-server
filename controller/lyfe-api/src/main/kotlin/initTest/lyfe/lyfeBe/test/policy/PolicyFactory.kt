package initTest.lyfe.lyfeBe.test.policy

import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.policy.PolicyUpdate

class PolicyFactory {

    companion object {
        fun createTestPolicy(
            id: Long = 1L,
            title: String = "testTitle",
            content: String = "testContent",
            version: String = "testVersion",
            policyType: PolicyType = PolicyType.PERSONAL_INFO_AGREEMENT
        ): Policy {
            return Policy(
                id,
                title,
                content,
                version,
                policyType
            )
        }

        fun createPolicyUpdate(
            id: Long = 1L,
            title: String = "testTitle",
            content: String = "testContent",
            version: String = "testVersion",
            policyType: PolicyType = PolicyType.PERSONAL_INFO_AGREEMENT
        ): PolicyUpdate {
            return PolicyUpdate(
                id,
                title,
                content,
                version,
                policyType
            )
        }
    }
}
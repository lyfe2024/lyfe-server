package lyfe.lyfeBe.persistence.policy

import lyfe.lyfeBe.policy.PolicyType
import org.springframework.data.jpa.repository.JpaRepository

interface PolicyRepository: JpaRepository<PolicyJpaEntity, Long> {
    fun findByPolicyType(term: PolicyType): PolicyJpaEntity
}
package lyfe.lyfeBe.persistence.policy

import org.springframework.data.jpa.repository.JpaRepository

interface PolicyRepository: JpaRepository<lyfe.lyfeBe.persistence.policy.PolicyJpaEntity, Long> {
}
package lyfe.lyfeBe.persistence.policy

import lyfe.lyfeBe.policy.domain.Policy

object PolicyMapper {

    fun mapToDomainEntity(policy: PolicyJpaEntity): Policy =
        Policy(
            id = policy.id,
            title = policy.title,
            content = policy.content,
            version = policy.version,
        )

    fun mapToJpaEntity(policy: Policy): PolicyJpaEntity =
        PolicyJpaEntity(
            id = policy.id,
            title = policy.title,
            content = policy.content,
            version = policy.version,
        )
}
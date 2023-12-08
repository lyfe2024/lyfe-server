package lyfe.lyfeBe.persistence.policy

import jakarta.persistence.*
import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyType
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "policy")
class PolicyJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotNull
    val title: String,

    @field:NotNull
    val content: String,

    @field:NotNull
    val version: String,


    @field:NotNull
    @Enumerated(EnumType.STRING)
    val policyType: PolicyType
) {
    fun toDomain(): Policy {
        return Policy(
            id = id,
            title = title,
            content = content,
            version = version,
            policyType = policyType
        )
    }

    companion object {
        fun from(policy: Policy) =
            PolicyJpaEntity(
                title = policy.title,
                content = policy.content,
                version = policy.version,
                policyType = policy.policyType
            )

        fun update(policy: Policy) =
            PolicyJpaEntity(
                id = policy.id,
                title = policy.title,
                content = policy.content,
                version = policy.version,
                policyType = policy.policyType
            )

    }
}
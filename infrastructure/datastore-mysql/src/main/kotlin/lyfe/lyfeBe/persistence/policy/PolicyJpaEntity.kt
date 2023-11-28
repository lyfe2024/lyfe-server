package lyfe.lyfeBe.persistence.policy

import jakarta.persistence.*
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
) {
}
package lyfe.lyfeBe.persistence.policy

import jakarta.persistence.*

@Entity
@Table(name = "policy")
class PolicyJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long = 0,
    var title: String,
    var content: String,
    var version: String,
) {
}
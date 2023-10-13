package lyfe.lyfeBe.domain.policy.model

import jakarta.persistence.*

@Entity
class Policy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long = 0,
    var title: String,
    var content: String,
    var version: String,
) {
}
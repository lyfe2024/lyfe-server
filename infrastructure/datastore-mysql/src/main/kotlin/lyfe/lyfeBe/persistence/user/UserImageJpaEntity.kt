package lyfe.lyfeBe.persistence.user

import jakarta.persistence.*
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.board.BoardJpaEntity
import org.jetbrains.annotations.NotNull
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "profile_image")
class UserImageJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotNull
    val url: String,

    val width: Int,

    val height: Int,

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_image_user_id"))
    val user: UserJpaEntity,

    @Embedded
    val baseEntity: BaseEntity

) {

}

package lyfe.lyfeBe.persistence.image

import jakarta.persistence.*
import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.image.ImageType
import lyfe.lyfeBe.persistence.BaseEntity
import lyfe.lyfeBe.persistence.board.BoardJpaEntity
import lyfe.lyfeBe.persistence.user.UserJpaEntity
import org.jetbrains.annotations.NotNull
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "image")
class ImageJpaEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @field:NotNull
        val url: String,

        val width: Int,

        val height: Int,

        @field:NotNull
        @Enumerated(EnumType.STRING)
        val type: ImageType,

        @ManyToOne
        @JoinColumn(name = "board_id", foreignKey = ForeignKey(name = "fk_image_board_id"))
        val board: BoardJpaEntity? = null,


        @OneToOne
        @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_image_user_id"))
        val user: UserJpaEntity? = null,

        @Embedded
        val baseEntity: BaseEntity? = null

) {
    fun toDomain(): Image {
        return Image(
                id = id,
                url = url,
                board = board?.toDomain(),
                user = user?.toDomain(),
                type = type,
                width = width,
                height = height
        )
    }

    companion object {
        fun from(image: Image): ImageJpaEntity {
            return ImageJpaEntity(
                    id = image.id,
                    url = image.url,
                    type = image.type,
                    width = image.width,
                    height = image.height
            )
        }
    }

}

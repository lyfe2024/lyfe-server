package lyfe.lyfeBe.image

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserUpdate

data class Image(
    val id: Long,
    val url: String,
    val board: Board?,
    val user: User?,
    val type: ImageType,
    val width: Int,
    val height: Int
) {

    fun update(userUpdate: UserUpdate): Image {
        return Image(
            id = id,
            url = userUpdate.profileUrl,
            board = board,
            user = user,
            type = type,
            width = userUpdate.width,
            height = userUpdate.height
        )
    }


}
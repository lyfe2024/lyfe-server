package lyfe.lyfeBe.image

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.user.User

data class Image(
        val id: Long,
        val url: String,
        val board: Board?,
        val user: User?,
        val type: ImageType,
        val width: Int,
        val height: Int
) {

}
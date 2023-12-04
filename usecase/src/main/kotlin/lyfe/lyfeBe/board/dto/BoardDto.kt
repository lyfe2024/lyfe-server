package lyfe.lyfeBe.board.dto

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.user.dto.UserDTO

data class BoardDto(
        val id: Long,
        val user: UserDTO,
        val title: String,
        val content: String,
        val boardType: String,
        val updatedAt: String
)
{
    companion object {
        fun from(board: Board , url : String): BoardDto {
            return BoardDto(
                    id = board.id,
                    user = UserDTO.from(board.user , url),
                    title = board.title,
                    content = board.content,
                    boardType = board.boardType.name,
                    updatedAt = board.updatedAt.toString()
            )
        }
    }
}
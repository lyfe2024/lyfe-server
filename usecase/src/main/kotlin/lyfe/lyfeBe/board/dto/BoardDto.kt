package lyfe.lyfeBe.board.dto

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.user.dto.UserDTO

data class BoardDto(
        val id: Long,
        val user: UserDTO,
        val title: String,
        val content: String,
        val boardType: BoardType,
        val whiskyCount: String,
        val commentCount: String,
        val updatedAt: String
) {
    companion object {
        fun toDto(param : BoardDtoAssembly): BoardDto {
            return BoardDto(
                    id = param.board.id,
                    user = UserDTO.from(param.board.user, param.imageUrl),
                    title = param.board.title,
                    content = param.board.content,
                    boardType = param.board.boardType,
                    whiskyCount = param.whiskyCount.toString(),
                    commentCount = param.commentCount.toString(),
                    updatedAt = param.board.updatedAt.toString()
            )
        }
    }
}
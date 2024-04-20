package lyfe.lyfeBe.board.dto

import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.user.dto.UserDto

data class BoardDto(
    val id: Long,
    val user: UserDto,
    val title: String,
    val content: String,
    val topic : String,
    val imageUrl: String? = null,
    val boardType: BoardType,
    val whiskyCount: Int? = 0,
    val commentCount: Int? = 0,
    val updatedAt: String
) {
    companion object {
        fun toBoardDto(param : BoardDtoAssembly): BoardDto {
            return BoardDto(
                    id = param.board.id,
                    user = UserDto.from(param.board.user),
                    title = param.board.title,
                    content = param.board.content,
                    topic = param.board.topic.content,
                    imageUrl = param.board.imageUrl,
                    boardType = param.board.boardType,
                    whiskyCount = param.whiskyCount,
                    commentCount = param.commentCount,
                    updatedAt = param.board.updatedAt.toString()
            )
        }
    }
}
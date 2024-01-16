package lyfe.lyfeBe.comment.dto

import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.user.dto.UserDto

data class CommentDto(
    val id: Long,
    val content: String,
    val commentGroupId: Long?,
    val user : UserDto,
    val createdAt: String
){
    companion object {
        fun from(comment : Comment): CommentDto {
            return CommentDto(
                id = comment.id,
                content = comment.content,
                commentGroupId = comment.commentGroupId,
                user = UserDto.from(comment.user),
                createdAt = comment.createdAt.toString()
            )
        }
    }
}

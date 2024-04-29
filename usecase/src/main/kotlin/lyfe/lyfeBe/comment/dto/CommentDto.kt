package lyfe.lyfeBe.comment.dto

import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.user.dto.UserDto

data class CommentDto(
    val id: Long,
    val content: String,
    val commentGroupId: Long?,
    val user: UserDto,
    val createdAt: String,
    val replies: List<CommentDto> = listOf()  // 대댓글 목록을 포함
) {
    companion object {
        fun from(comment: Comment, replies: List<Comment> = listOf()): CommentDto {
            return CommentDto(
                id = comment.id,
                content = comment.content,
                commentGroupId = comment.commentGroupId,
                user = UserDto.from(comment.user),
                createdAt = comment.createdAt.toString(),
                replies = replies.map { from(it) }  // 대댓글을 DTO로 변환
            )
        }
    }
}

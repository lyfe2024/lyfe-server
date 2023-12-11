package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.web.user.UserResponse


object CommentMapper {
    fun mapToResponse(comment: Comment): CommentResponse =
        CommentResponse(
            id = comment.id,
            content = comment.content,
            commentGroupId = comment.commentGroupId,
            user = UserResponse(
                id = comment.user.id,
                username = comment.user.nickname,
                profile = "",
            ),
            createdAt = comment.createdAt.toString()
        )
}
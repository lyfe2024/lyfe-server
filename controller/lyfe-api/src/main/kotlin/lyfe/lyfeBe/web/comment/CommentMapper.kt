package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.comment.Comment


object CommentMapper {
    fun mapToResponse(comment: Comment): CommentResponse =
        CommentResponse(
            id = comment.id,
            content = comment.content,
            commentGroupId = comment.commentGroupId,
            userId = comment.user.id,
            boardId = comment.board.id,
            createdAt = comment.createdAt,
            updatedAt = comment.updatedAt,
            visibility = comment.visibility,
            )
}
package lyfe.lyfeBe.comment.port.`in`

import lyfe.lyfeBe.comment.Comment

fun interface CreateCommentUseCase {
    fun create(
        content: String,
        commentGroupId: Long?,
        userId: Long,
        boardId: Long,
    ) : Comment
}
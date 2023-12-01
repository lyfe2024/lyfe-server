package lyfe.lyfeBe.comment.port.`in`

import lyfe.lyfeBe.comment.Comment

fun interface UpdateCommentUseCase {
    fun update(
        commentId: Long,
        content: String,
        userId: Long,
    ): Comment
}
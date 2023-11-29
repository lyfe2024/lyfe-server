package lyfe.lyfeBe.comment.port.out

import lyfe.lyfeBe.comment.Comment

interface SaveCommentPort {
    fun create(comment: Comment): Comment
}
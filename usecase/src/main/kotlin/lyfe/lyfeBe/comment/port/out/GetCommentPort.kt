package lyfe.lyfeBe.comment.port.out

import lyfe.lyfeBe.comment.Comment

interface GetCommentPort {
    fun getById(id: Long): Comment
}
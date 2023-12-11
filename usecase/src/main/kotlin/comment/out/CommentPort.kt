package comment.out

import lyfe.lyfeBe.comment.Comment

interface CommentPort {
    fun countByBoardId(boardId: Long): Int
    fun create(comment: Comment) : Comment
}
package lyfe.lyfeBe.comment.port.out

import lyfe.lyfeBe.comment.Comment

interface CommentPort {

    fun getById(id: Long): Comment
    fun getCommentsWithCursorAndBoard(
        cursorId: Long,
        boardId: Long
    ): List<Comment>
    fun getCommentsWithCursorAndUser(
        cursorId: Long,
        userId: Long
    ): List<Comment>
    fun findLastByBoardId(boardId: Long): Comment

    fun create(comment: Comment): Comment
    fun update(comment: Comment): Comment

    fun countByBoardId(boardId: Long): Int
}
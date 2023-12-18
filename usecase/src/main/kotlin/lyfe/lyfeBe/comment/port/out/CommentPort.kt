package lyfe.lyfeBe.comment.port.out

import lyfe.lyfeBe.comment.Comment
import org.springframework.data.domain.Pageable

interface CommentPort {

    fun getById(id: Long): Comment
    fun getCommentsWithCursorAndBoard(
        cursorId: Long,
        boardId: Long,
        pageable: Pageable
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
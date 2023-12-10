/*
package lyfe.lyfeBe.comment.port.out

import lyfe.lyfeBe.comment.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GetCommentPort {
    fun getById(id: Long): Comment
    fun findAllByBoardId(
        cursorId: Long,
        boardId: Long,
        pageable: Pageable
    ): Page<Comment>
    fun findAllByUserId(
        cursorId: Long,
        userId: Long,
        pageable: Pageable
    ): Page<Comment>
    fun findLastByBoardId(boardId: Long): Comment
}*/

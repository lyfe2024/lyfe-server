package initTest.lyfe.lyfeBe.test.comment

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.user.User
import java.time.Instant

class CommentFactory {
    companion object {
        fun createTestComment(
            id: Long = 1L,
            content: String = "이것은 테스트 코멘트입니다.",
            commentGroupId: Long = 1L,
            user: User,
            board: Board,
            createdAt: Instant = Instant.now(),
            updatedAt: Instant = Instant.now()
        ): Comment {
            return Comment(
                id = id,
                content = content,
                commentGroupId = commentGroupId,
                user = user,
                board = board,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
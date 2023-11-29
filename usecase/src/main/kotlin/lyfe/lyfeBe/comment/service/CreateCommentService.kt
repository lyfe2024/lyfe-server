package lyfe.lyfeBe.comment.service

import lyfe.lyfeBe.board.port.out.GetBoardPort
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.comment.port.`in`.CreateCommentUseCase
import lyfe.lyfeBe.comment.port.out.GetCommentPort
import lyfe.lyfeBe.comment.port.out.SaveCommentPort
import lyfe.lyfeBe.user.port.out.GetUserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CreateCommentService(
    private val saveCommentPort: SaveCommentPort,
    private val getUserPort: GetUserPort,
    private val getBoardPort: GetBoardPort,
    private val getCommentPort: GetCommentPort
) : CreateCommentUseCase {

    override fun create(
        content: String,
        commentGroupId: Long?,
        userId: Long,
        boardId: Long
    ): Comment {

        val user = getUserPort.getById(id = userId)

        val board = getBoardPort.getById(id = boardId)

        if (commentGroupId != null) {
            val groupComment = getCommentPort.getById(id = commentGroupId)
            require(groupComment.commentGroupId == null) { "대댓글은 대댓글을 달 수 없습니다." }
        }

        return Comment.create(
            content = content,
            commentGroupId = commentGroupId,
            user = user,
            board = board
        ).let { saveCommentPort.create(it) }
    }
}
package lyfe.lyfeBe.comment.service

import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.comment.port.`in`.UpdateCommentUseCase
import lyfe.lyfeBe.comment.port.out.GetCommentPort
import lyfe.lyfeBe.comment.port.out.SaveCommentPort
import lyfe.lyfeBe.error.ForbiddenException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UpdateCommentService(
    private val saveCommentPort: SaveCommentPort,
    private val getCommentPort: GetCommentPort
) : UpdateCommentUseCase {
    override fun update(
        commentId: Long,
        content: String,
        userId: Long
    ): Comment {
        val comment = getCommentPort.getById(id = commentId)

        if (comment.user.id != userId) {
            throw ForbiddenException("자신의 댓글만 수정할 수 있습니다.")
        }

        return if (comment.content == content) {
            comment
        } else {
            comment.content = content
            saveCommentPort.update(comment)
        }
    }
}
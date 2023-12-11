package lyfe.lyfeBe.comment.service

import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.comment.UpdateComment
import lyfe.lyfeBe.comment.dto.CommentDto
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.error.ForbiddenException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UpdateCommentService(
    private val commentPort: CommentPort
) {
    fun update(command : UpdateComment): CommentDto {
        val comment = commentPort.getById(id = command.commentId)

        if (comment.user.id != command.userId) {
            throw ForbiddenException("자신의 댓글만 수정할 수 있습니다.")
        }

        return if (comment.content == command.content) {
            comment
        } else {
            comment.content = command.content
            commentPort.update(comment)
        }.let { CommentDto.from(it) }
    }
}
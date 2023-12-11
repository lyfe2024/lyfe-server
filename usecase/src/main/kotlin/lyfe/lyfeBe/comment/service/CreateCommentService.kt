package lyfe.lyfeBe.comment.service

import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.comment.dto.CommentDto
import lyfe.lyfeBe.comment.CreateComment
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CreateCommentService(
    private val commentPort: CommentPort,
    private val userPort: UserPort,
    private val boardPort: BoardPort,
) {
    fun create(command: CreateComment): CommentDto {

        val user = userPort.getById(userId = command.userId)
        val board = boardPort.findById(id = command.boardId)

        command.commentGroupId?.let {
            val groupComment = commentPort.getById(id = it)
            require(groupComment.commentGroupId == null) { "대댓글은 대댓글을 달 수 없습니다." }
        }

        val comment = Comment.create(
            content = command.content,
            commentGroupId = command.commentGroupId,
            user = user,
            board = board
        )

        return commentPort.create(comment).let { CommentDto.from(it) }
    }
}
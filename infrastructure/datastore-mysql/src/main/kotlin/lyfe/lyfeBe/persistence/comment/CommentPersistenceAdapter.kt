package lyfe.lyfeBe.persistence.comment

import comment.out.CommentPort
import lyfe.lyfeBe.comment.Comment
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class CommentPersistenceAdapter(
    private val commentRepository: CommentRepository
) : CommentPort {
    override fun countByBoardId(boardId: Long) = commentRepository.countByBoardId(boardId)
    override fun create(comment: Comment) =
        commentRepository.save(CommentJpaEntity.from(comment)).toDomain()
}
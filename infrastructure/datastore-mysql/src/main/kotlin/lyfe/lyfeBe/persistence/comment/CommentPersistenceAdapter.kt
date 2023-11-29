package lyfe.lyfeBe.persistence.comment

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

    override fun getById(id: Long): Comment =
        commentRepository.findByIdOrNull(id)
            ?.let { CommentMapper.mapToDomainEntity(it) }
            ?: throw ResourceNotFoundException("해당하는 댓글이 존재하지 않습니다.")

}
package lyfe.lyfeBe.persistence.comment

import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.error.ResourceNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class CommentPersistenceAdapter(
    private val commentRepository: CommentRepository
) : CommentPort {
    override fun countByBoardId(boardId: Long) = commentRepository.countByBoardId(boardId)

    @Transactional
    override fun create(comment: Comment): Comment {
        check(comment.id == 0L) { "ID가 0 or null이 아니면 생성할 수 없습니다." }
        return commentRepository.save(CommentJpaEntity.from(comment)).toDomain()
    }

    @Transactional
    override fun update(comment: Comment): Comment {
        check(comment.id != 0L) { "ID가 0인 Comment는 업데이트할 수 없습니다." }
        return commentRepository.save(CommentJpaEntity.from(comment)).toDomain()
    }

    override fun getById(id: Long): Comment =
        commentRepository.findByIdOrNull(id)?.toDomain()
            ?: throw ResourceNotFoundException("해당하는 댓글이 존재하지 않습니다.")

    override fun getCommentsWithCursorAndBoard(
        cursorId: Long,
        boardId: Long,
        pageable: Pageable
    ): List<Comment> {
        return commentRepository.findAllByBoardIdAndIdLessThanOrderByIdDesc(boardId, cursorId,  pageable)
            .map { it.toDomain() }.toList()
    }

    override fun getCommentsWithCursorAndUser(
        cursorId: Long,
        userId: Long
    ): List<Comment> {
        return commentRepository.findAllByUserIdAndIdLessThanOrderByIdDesc(userId, cursorId)
            .map { it.toDomain() }
    }

    override fun findLastByBoardId(boardId: Long): Comment {
        return commentRepository.findFirstByBoardIdOrderByIdDesc(boardId)?.toDomain()
            ?: throw ResourceNotFoundException("해당하는 댓글이 존재하지 않습니다.")
    }


}
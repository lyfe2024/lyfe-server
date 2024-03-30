package lyfe.lyfeBe.persistence.board

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.error.ResourceNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class BoardPersistenceAdapter(
    private val boardJpaRepository: BoardJpaRepository,
) : BoardPort {
    override fun getById(id: Long): Board {
        return boardJpaRepository.findByIdOrNull(id)?.toDomain()
            ?: throw ResourceNotFoundException("해당하는 게시글이 존재하지 않습니다.")
    }

    override fun create(board: Board) =
        boardJpaRepository.save(BoardJpaEntity.from(board)).toDomain()

    override fun update(board: Board): Board {
        val update = BoardJpaEntity.update(board)
        return boardJpaRepository.save(update).toDomain()
    }

    override fun findPopularBoardsWithWhisky(
        cursorId: Long,
        topicId: Long,
        type: BoardType,
        pageable: Pageable
    ): List<Board> {
        return boardJpaRepository.findPopularBoardsWithWhisky(
            cursorId = cursorId,
            topicId = topicId,
            type = type,
            pageCount = pageable.pageSize
        ).map { it.toDomain() }
    }

    override fun findPopularBoardsWithComment(
        cursorId: Long,
        topicId: Long,
        type: BoardType,
        pageable: Pageable
    ): List<Board> {
        return boardJpaRepository.findPopularBoardsWithComment(
            cursorId = cursorId,
            topicId = topicId,
            type = type,
            pageCount = pageable.pageSize
        ).map { it.toDomain() }
    }

    override fun getBoardsWithCursorAndUser(
        cursorId: Long,
        type: BoardType,
        userId: Long,
        pageable: Pageable
    ): List<Board> {
        return boardJpaRepository.findAllByUserIdAndBoardTypeAndIdLessThanOrderByIdDesc(
            userId,
            type,
            cursorId,
            pageable
        ).map { it.toDomain() }
    }

    override fun getBoardWithCursorAndTopic(
        cursorId: Long,
        type: BoardType,
        topicId: Long?,
        pageable: Pageable
    ): List<Board> {
        return boardJpaRepository.findAllByBoardTypeAndTopicIdAndIdLessThanOrderByIdDesc(
            type,
            topicId,
            cursorId,
            pageable
        ).map { it.toDomain() }
    }


}
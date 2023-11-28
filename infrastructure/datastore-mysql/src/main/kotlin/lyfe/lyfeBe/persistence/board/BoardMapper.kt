package lyfe.lyfeBe.persistence.board

import lyfe.lyfeBe.persistence.topic.TopicMapper
import lyfe.lyfeBe.persistence.user.UserMapper
import lyfe.lyfeBe.board.Board

object BoardMapper {

    fun mapToDomainEntity(board: BoardJpaEntity): Board =
        Board(
            id = board.id,
            title = board.title,
            content = board.content,
            picture = board.picture,
            boardType = board.boardType,
            user = UserMapper.mapToDomainEntity(board.user),
            topic = TopicMapper.mapToDomainEntity(board.topic),
            createdAt = board.createdAt,
            updatedAt = board.updatedAt,
            deletedAt = board.deletedAt,
            visibility = board.visibility,
        )

    fun mapToJpaEntity(board: Board): BoardJpaEntity =
        BoardJpaEntity(
            id = board.id,
            title = board.title,
            content = board.content,
            picture = board.picture,
            boardType = board.boardType,
            user = UserMapper.mapToJpaEntity(board.user),
            topic = TopicMapper.mapToJpaEntity(board.topic),
            deletedAt = board.deletedAt
        ).apply {
            createdAt = board.createdAt
            updatedAt = board.updatedAt
            visibility = board.visibility
        }
}
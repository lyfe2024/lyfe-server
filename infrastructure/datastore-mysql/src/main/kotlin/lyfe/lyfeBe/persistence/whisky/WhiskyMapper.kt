package lyfe.lyfeBe.persistence.whisky

import lyfe.lyfeBe.persistence.board.BoardMapper
import lyfe.lyfeBe.persistence.user.UserMapper
import lyfe.lyfeBe.whisky.Whisky

object WhiskyMapper {

    fun mapToDomainEntity(whisky: WhiskyJpaEntity): Whisky =
        Whisky(
            id = whisky.id,
            user = UserMapper.mapToDomainEntity(whisky.user),
            board = BoardMapper.mapToDomainEntity(whisky.board),
            createdAt = whisky.createdAt,
        )

    fun mapToJpaEntity(whisky: Whisky): WhiskyJpaEntity =
        WhiskyJpaEntity(
            id = whisky.id,
            user = UserMapper.mapToJpaEntity(whisky.user),
            board = BoardMapper.mapToJpaEntity(whisky.board),
            createdAt = whisky.createdAt
        )
}

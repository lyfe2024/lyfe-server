package lyfe.lyfeBe.persistence.board

import lyfe.lyfeBe.board.Board

data class BoardWithCursorValue(
    val board: BoardJpaEntity,
    val whiskyCount: Long,
    val cursorValue: String
) {
    fun toDomain(): Board {
        return Board(
            id = board.id,
            title = board.title,
            content = board.content,
            boardType = board.boardType,
            imageUrl = board.imageUrl,
            user = board.user.toDomain(), // UserJpaEntity를 User 도메인 객체로 변환
            topic = board.topic.toDomain(), // TopicJpaEntity를 Topic 도메인 객체로 변환
            whiskyCount = whiskyCount.toInt(),
            createdAt = board.baseEntity.createdAt,
            updatedAt = board.baseEntity.updatedAt
        )
    }}

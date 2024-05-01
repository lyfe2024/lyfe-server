package lyfe.lyfeBe.whisky.out

import lyfe.lyfeBe.whisky.Whisky

interface WhiskyPort {
    fun existByBoardIdAndUserId(boardId: Long, userId: Long): Boolean
    fun deleteByBoardIdAndUserId(boardId: Long, userId: Long)

    fun create(whisky: Whisky): Whisky

    fun countByBoardId(boardId: Long): Int
}
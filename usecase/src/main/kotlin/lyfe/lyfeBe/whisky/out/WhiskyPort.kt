package lyfe.lyfeBe.whisky.out

import lyfe.lyfeBe.whisky.Whisky

interface WhiskyPort {
    fun countByBoardId(boardId: Long): Int
    fun create(whisky: Whisky): Whisky
    fun update(boardId: Long)
    fun assertNoExistingWhisky(boardId: Long, userId: Long): Whisky?
    fun delete(boardId: Long, userId: Long)

    fun get(whiskyId: Long): Whisky

}
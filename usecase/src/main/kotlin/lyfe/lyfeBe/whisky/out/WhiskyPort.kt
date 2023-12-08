package lyfe.lyfeBe.whisky.out

import lyfe.lyfeBe.whisky.Whisky

interface WhiskyPort {
    fun countByBoardId(boardId: Long): Int
    fun create(whisky: Whisky) : Whisky
}
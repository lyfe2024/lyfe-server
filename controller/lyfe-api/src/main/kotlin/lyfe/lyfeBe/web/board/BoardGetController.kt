package lyfe.lyfeBe.web.board

import lyfe.lyfeBe.board.BoardGet
import lyfe.lyfeBe.board.BoardService
import lyfe.lyfeBe.board.BoardsGet
import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.dto.CommonResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("boards")
class BoardGetController(
    private val service: BoardService
) {
    @GetMapping
    fun getBoards(
        @RequestParam(required = false) cursorId: Long?,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): CommonResponse<List<BoardDto>> {
        val boardId = getEffectiveCursorId(cursorId)
        return CommonResponse(service.getBoards(BoardsGet(boardId, size)))
    }


    @GetMapping("{boardId}")
    fun get(
        @PathVariable(value = "boardId") boardId: Long,
    ) = CommonResponse(service.get(BoardGet(boardId)))


    private fun getEffectiveCursorId(cursorId: Long?): Long {
        return cursorId ?: Long.MAX_VALUE
    }
}

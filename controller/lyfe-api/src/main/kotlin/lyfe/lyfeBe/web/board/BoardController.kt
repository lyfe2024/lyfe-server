package lyfe.lyfeBe.web.board

import jakarta.validation.Valid
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.board.dto.SaveBoardDto
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.web.board.req.BoardSaveRequest
import lyfe.lyfeBe.web.board.req.BoardUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/boards")
class BoardController(
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


    @GetMapping("/{boardId}")
    fun get(
        @PathVariable(value = "boardId") boardId: Long,
    ) = CommonResponse(service.get(BoardGet(boardId)))


    private fun getEffectiveCursorId(cursorId: Long?): Long {
        return cursorId?.takeIf { it != 0L } ?: Long.MAX_VALUE
    }

    @PostMapping
    fun create(
        @Valid @RequestBody req: BoardSaveRequest
    ): CommonResponse<SaveBoardDto> = service.create(
        BoardCreate(
            title = req.title,
            content = req.content,
            boardType = req.boardType,
            userId = req.userId,
            topicId = req.topicId
        )
    ).let { CommonResponse(it) }




    @PutMapping("/{boardId}")
    fun update(
        @PathVariable(value = "boardId") boardId: Long,
        @Valid @RequestBody req: BoardUpdateRequest
    ): CommonResponse<SaveBoardDto> =
        service.update(
            BoardUpdate(
                boardId = boardId,
                title = req.title,
                content = req.content
            )
        ).let { CommonResponse(it) }

}

package lyfe.lyfeBe.web.board

import jakarta.validation.Valid
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.web.board.req.BoardSaveReq
import lyfe.lyfeBe.web.board.req.BoardUpdateReq
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("board")
class BoardController(
    private val service: BoardService
) {


    @GetMapping("{boardId}")
    fun get(
        @PathVariable(value = "boardId") boardId: Long,
    ) = service.get(BoardGet(boardId))


    @GetMapping
    fun getBoards(
        @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
        @RequestParam(value = "pageSize", required = false, defaultValue = "10") pageSize: Int
    ) = service.getBoards(BoardsGet(page, pageSize))


    @PostMapping
    fun create(
        @Valid @RequestBody req: BoardSaveReq
    ) {
        service.create(
            BoardCreate(
                req.title,
                req.content,
                req.boardType,
                req.userId,
                req.topicId
            )
        )
    }


    @PutMapping("{boardId}")
    fun update(
        @PathVariable(value = "boardId") boardId: Long,
        @Valid @RequestBody req: BoardUpdateReq
    ) {
        service.update(
            BoardUpdate(
                boardId,
                req.title,
                req.content
            )
        )
    }
}

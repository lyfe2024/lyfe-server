package lyfe.lyfeBe.web.board

import jakarta.validation.Valid
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.web.board.req.BoardSaveReq
import lyfe.lyfeBe.web.board.req.BoardUpdateReq
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("boards")
class BoardGetController(
    private val service: BoardService
) {


    @GetMapping("{boardId}")
    fun get(
        @PathVariable(value = "boardId") boardId: Long,
    ) = service.get(BoardGet(boardId))

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

package lyfe.lyfeBe.web.board

import jakarta.validation.Valid
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.web.board.req.BoardUpdateReq
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("boards")
class BoardUpdateController(
    private val service: BoardService
) {

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

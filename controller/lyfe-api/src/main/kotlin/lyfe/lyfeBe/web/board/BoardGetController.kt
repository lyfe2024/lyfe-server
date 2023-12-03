package lyfe.lyfeBe.web.board

import lyfe.lyfeBe.board.BoardGet
import lyfe.lyfeBe.board.BoardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("boards")
class BoardGetController(
    private val service: BoardService
) {


    @GetMapping("{boardId}")
    fun get(
        @PathVariable(value = "boardId") boardId: Long,
    ) = service.get(BoardGet(boardId))
}

package lyfe.lyfeBe.web.board

import jakarta.validation.Valid
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.web.board.req.BoardSaveReq
import lyfe.lyfeBe.web.board.req.BoardUpdateReq
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("boards")
class BoardCreateController(
    private val service: BoardService
) {
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
}

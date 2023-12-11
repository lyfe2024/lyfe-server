package lyfe.lyfeBe.web.board

import jakarta.validation.Valid
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.web.board.req.BoardUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/boards")
class UpdateBoardController(
    private val service: BoardService
) {

    @PutMapping("{boardId}")
    fun update(
        @PathVariable(value = "boardId") boardId: Long,
        @Valid @RequestBody req: BoardUpdateRequest
    ): CommonResponse<Long> =
        service.update(
            BoardUpdate(
                boardId,
                req.title,
                req.content
            )
        ).let { CommonResponse(it) }

}

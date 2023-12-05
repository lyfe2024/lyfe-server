package lyfe.lyfeBe.web.board

import jakarta.validation.Valid
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardCreate
import lyfe.lyfeBe.board.BoardService
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.web.board.req.BoardSaveReq
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("boards")
class BoardCreateController(
        private val service: BoardService
) {
    @PostMapping
    fun create(
            @Valid @RequestBody req: BoardSaveReq
    ): CommonResponse<Board> = service.create(
            BoardCreate(
                    req.title,
                    req.content,
                    req.boardType,
                    req.userId,
                    req.topicId
            )
    ).let { CommonResponse(it) }
}

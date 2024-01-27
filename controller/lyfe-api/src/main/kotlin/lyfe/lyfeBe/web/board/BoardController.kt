package lyfe.lyfeBe.web.board

import jakarta.validation.Valid
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.utils.ControllerUtils.Companion.getEffectiveCursorId
import lyfe.lyfeBe.web.board.req.BoardSaveRequest
import lyfe.lyfeBe.web.board.req.BoardUpdateRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/boards")
class BoardController(
    private val service: BoardService
) {
    @GetMapping
    fun getBoards(
        @RequestParam(required = false) cursorId: Long?,
        @PageableDefault(size = 10, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
        ): CommonResponse<List<BoardDto>> {
        val boardId = getEffectiveCursorId(cursorId)
        return CommonResponse(service.getBoards(BoardsGet(boardId, pageable)))
    }



    @GetMapping("/popular/{boardId}/{whiskyCount}")
    fun getPopularBoards(
        @PathVariable boardId: Long,
        @PathVariable whiskyCount: Long,
        @PageableDefault(size = 10, page = 0) pageable: Pageable,
    ): CommonResponse<List<BoardDto>> {
        val boardId = getEffectiveCursorId(boardId)
        return CommonResponse(service.getPopularBoards(BoardsPopularGet(boardId,whiskyCount, pageable)))
    }


    @GetMapping("/picture")
    fun getPictureBoards(
        @RequestParam(required = false) cursorId: Long?,
        @PageableDefault(size = 10, page = 0) pageable: Pageable,
    ): CommonResponse<List<BoardDto>> {
        val boardId = getEffectiveCursorId(cursorId)
        return CommonResponse(service.getBoardPictures(BoardsGet(boardId, pageable)))
    }


    @GetMapping("/{boardId}")
    fun get(
        @PathVariable(value = "boardId") boardId: Long,
    ) = CommonResponse(service.get(BoardGet(boardId)))

    @PostMapping
    fun create(
        @Valid @RequestBody req: BoardSaveRequest
    ) = CommonResponse(
        service.create(
            BoardCreate(
                title = req.title,
                content = req.content,
                boardType = req.boardType,
                userId = req.userId,
                topicId = req.topicId,
                imageUrl = req.imageUrl
            )
        )
    )


    @PutMapping("/{boardId}")
    fun update(
        @PathVariable(value = "boardId") boardId: Long,
        @Valid @RequestBody req: BoardUpdateRequest
    ) = CommonResponse(
        service.update(
            BoardUpdate(
                boardId = boardId,
                title = req.title,
                content = req.content,
                imageUrl = req.imageUrl
            )
        )
    )
}
